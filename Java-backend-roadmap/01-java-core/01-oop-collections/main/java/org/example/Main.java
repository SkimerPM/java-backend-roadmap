package org.example;

import org.example.entities.Person;
import org.example.entities.User;
import org.example.generics.SkmRepository;
import org.example.notifications.Cancelable;
import org.example.notifications.EmailNotification;
import org.example.notifications.Notification;
import org.example.valueobjects.PhoneNumber;
import org.example.notifications.SmsNotification;
import org.example.valueobjects.Email;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== DEMO: OOP + Collections + Generics ===");
        System.out.println();

        demonstratePerson();
        demonstrateValueObjects();
        demonstrateNotificationsPolymorphism();
        demonstrateCancelable();
        demonstrateGenericRepository();

        System.out.println();
        System.out.println("=== FIN DE LA DEMO ===");
    }

    // 1) Persona: validación, strip, haveBirthday
    private static void demonstratePerson() {
        System.out.println("---- 1) Person (validación e inmutabilidad) ----");

        Person person = new Person("   Fabricio con espacios   ", 25);
        System.out.println("Nombre limpio: " + person.getName());
        System.out.println("Edad: " + person.getAge());
        System.out.println("Año de nacimiento (2026): " + person.getBirthYear(2026));

        Person older = person.haveBirthday();
        System.out.println("Edad original: " + person.getAge());
        System.out.println("Edad después de cumpleaños (nueva instancia): " + older.getAge());

        System.out.println();
    }

    // 2) Value objects: Email, PhoneNumber
    private static void demonstrateValueObjects() {
        System.out.println("---- 2) Value Objects (Email, PhoneNumber) ----");

        Email email1 = new Email("skimer@test.com");
        Email email2 = new Email("  skimer@test.com  "); // se limpia dentro del value object

        System.out.println("Email 1: " + email1.value());
        System.out.println("Email 2: " + email2.value());
        System.out.println("Emails iguales (equals): " + email1.equals(email2));
        System.out.println("Mismo hashCode: " + (email1.hashCode() == email2.hashCode()));

        PhoneNumber phone1 = new PhoneNumber("987654321");
        PhoneNumber phone2 = new PhoneNumber("  987654321  ");
        System.out.println("Teléfonos iguales: " + phone1.equals(phone2));

        System.out.println();
    }

    // 3) Polimorfismo con Notification
    private static void demonstrateNotificationsPolymorphism() {
        System.out.println("---- 3) Polimorfismo con Notification ----");

        List<Notification> notifications = new ArrayList<>();

        Email email1 = new Email("user1@company.com");
        notifications.add(new EmailNotification(email1, "Tu sprint review es mañana a las 10 AM."));

        PhoneNumber phone1 = new PhoneNumber("987654321");
        notifications.add(new SmsNotification(phone1, "Código de verificación: 123456"));

        Email email2 = new Email("skimer@backend.dev");
        notifications.add(new EmailNotification(email2, "Tu refactor de hoy quedó muy limpio."));

        sendAll(notifications);

        System.out.println();
    }

    // 4) Interfaz Cancelable
    private static void demonstrateCancelable() {
        System.out.println("---- 4) Interfaz Cancelable ----");

        List<Cancelable> cancelables = new ArrayList<>();

        Email email = new Email("alerts@system.com");
        EmailNotification emailNotif = new EmailNotification(email, "Maintenance programado para esta noche.");

        PhoneNumber phone = new PhoneNumber("999888777");
        SmsNotification smsNotif = new SmsNotification(phone, "Tu pedido está en camino.");

        cancelables.add(emailNotif);
        cancelables.add(smsNotif);

        System.out.println("Estado inicial:");
        for (Cancelable c : cancelables) {
            System.out.println("- " + c.getClass().getSimpleName() + " cancelada: " + c.isCancelled());
        }

        System.out.println("Cancelando todas...");
        cancelAll(cancelables);

        System.out.println("Estado final:");
        for (Cancelable c : cancelables) {
            System.out.println("- " + c.getClass().getSimpleName() + " cancelada: " + c.isCancelled());
        }

        System.out.println();
    }

    // 5) Repositorio genérico con User
    private static void demonstrateGenericRepository() {
        System.out.println("---- 5) SkmRepository<User, String> ----");

        SkmRepository<User, String> userRepo = new SkmRepository<>();

        User user1 = new User("Gloria", new Email("gloria@ing.com"));
        User user2 = new User("Antuanet", new Email("antuanet@ing.com"));
        User user3 = new User("Fabricio", new Email("fabricio@dev.com"));

        userRepo.save(user1.getId(), user1);
        userRepo.save(user2.getId(), user2);
        userRepo.save(user3.getId(), user3);

        System.out.println("Usuarios guardados: 3");

        Optional<User> found = userRepo.findById(user2.getId());
        found.ifPresent(u -> System.out.println("Encontrado por ID: " + u.getName() + " (" + u.getEmail().value() + ")"));

        System.out.println("Lista completa de usuarios:");
        for (User u : userRepo.findAll()) {
            System.out.println("- " + u.getName() + " | " + u.getEmail().value());
        }

        System.out.println("Probando duplicado (debe lanzar excepción):");
        try {
            userRepo.save(user1.getId(), user1);
        } catch (IllegalArgumentException e) {
            System.out.println("Error esperado: " + e.getMessage());
        }

        userRepo.deleteById(user2.getId());
        System.out.println("Usuario Antuanet eliminado.");

        System.out.println("Usuarios restantes:");
        for (User u : userRepo.findAll()) {
            System.out.println("- " + u.getName());
        }

        System.out.println();
    }

    // helpers

    public static void sendAll(List<Notification> list) {
        for (Notification notification : list) {
            System.out.println(notification.format());
        }
    }

    public static void cancelAll(List<Cancelable> cancelables) {
        for (Cancelable cancelable : cancelables) {
            cancelable.cancel();
        }
    }
}
