import valueobjects.Employee;

import java.util.List;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args){
        List<Employee> employees = List.of(
                new Employee(1, "Skimer", "IT", 3000),
                new Employee(2, "Jose", "TRU", 1000),
                new Employee(3, "Gloria", "IT", 3900),

                new Employee(3, "Antuanet", "CDD", 6000),
                new Employee(4, "Fabricio", "CDD", 3400)
                );
        System.out.println("---------PROCESANDO DATOS----------");
        Stream<String> itEmployeeName = employees.stream()
                .filter(emp -> emp.salary() > 3500)
                .map(Employee::department).distinct();
        itEmployeeName.forEach(System.out::println);
    }
}
