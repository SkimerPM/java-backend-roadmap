import valueobjects.Employee;

import java.util.Collection;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DepartmentAnalysis {

    private static final List<Employee> EMPLOYEES = List.of(
            new Employee(1, "Skimer", "IT", 5000),
            new Employee(2, "Gloria", "HR", 6000),
            new Employee(3, "José", "IT", 6000),
            new Employee(4, "Fabricio", "Sales", 4000),
            new Employee(5, "Antuanet", "Sales", 4500)
    );

    public static void main(String[] args){
        System.out.println("Departamentos con sueldos competitivos");
        getHighPaidDepartments(5000).forEach(System.out::println);
        //Solo nombres
        System.out.println(getEmployeesJSON().values());
        //Departamento y nombres de las personas que pertenecen a ese departamento.
        System.out.println(getEmployeesJSON());
        //Con formato :D
        getEmployeesJSON().forEach((department, names) -> {
            System.out.println("Departamento: " +department+ " -> Empleados: " + names);
        });
        //Datos estadísticos (para algo estilo reportes podría ser, así me evito de hacer un stream para cada dato)
        DoubleSummaryStatistics stats = EMPLOYEES.stream().collect(Collectors.summarizingDouble(Employee::salary));
        System.out.println("Conteo: " + stats.getCount());
        System.out.println("Mínimo: " + stats.getMin());
        System.out.println("Máximo: " + stats.getMax());
        System.out.println("Promedio: " + stats.getAverage());
        System.out.println("Suma Total: " + stats.getSum());

        boolean existsRichEmployee = EMPLOYEES.stream()
                .anyMatch(e -> e.salary() > 2000); // Se detiene en cuanto encuentra el primero.
        System.out.println(existsRichEmployee);
    }

    //Lista simple de Departamentos
    public static List<String> getHighPaidDepartments(double minSalary){
        return EMPLOYEES.stream()
                    .filter(emp -> emp.salary() > minSalary)
                .map(Employee::department).distinct().toList();
    }
    public static Map<String, List<String>> getEmployeesJSON(){
        return EMPLOYEES.stream().collect(Collectors.groupingBy(Employee::department, Collectors.mapping(Employee::name, Collectors.toList())));
    }

}
