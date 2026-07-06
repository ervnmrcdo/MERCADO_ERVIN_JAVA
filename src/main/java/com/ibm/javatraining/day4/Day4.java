package com.ibm.javatraining.day4;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;


class Employee {
    private String name;
    private String department;
    private double salary;

    Employee(String name, String department, int salary) {
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    public double getSalary() {
        return salary;
    }

    public String getDepartment() {
        return department;
    }

    public String getName() {
        return name;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return name + " ($" + salary + ")";
    }
}

public class Day4 {

    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Alice", "IT", 55000));
        employees.add(new Employee("Bob", "Finance", 60000));
        employees.add(new Employee("Alice", "HR", 52000)); // duplicate name
        employees.add(new Employee("Ken", "IT", 60000));
        employees.add(new Employee("Maria", "HR", 50000));
        employees.add(new Employee("John", "Finance", 70000));
        employees.add(new Employee("Ken", "Finance", 65000)); // duplicate name
        employees.add(new Employee("Lara", "IT", 62000));
        employees.add(new Employee("Sam", "HR", 48000));
        employees.add(new Employee("Bob", "IT", 59000)); // duplicate name

        Set<String> employeeNames = new HashSet<String>();
        Map<String, List<Employee>> groupedByDepartment = new HashMap<>();
        Set<Double> uniqueSalaries = new TreeSet<>();
        employees.sort(Comparator.comparing(Employee::getSalary));

        for (Employee e : employees) {
            employeeNames.add(e.getName());
            uniqueSalaries.add(e.getSalary());

            if (!groupedByDepartment.containsKey(e.getDepartment())) {
                groupedByDepartment.put(e.getDepartment(), new ArrayList<>());
            }

            groupedByDepartment.get(e.getDepartment()).add(e);
        }

        employees.sort(Comparator.comparing(Employee::getSalary).reversed());

        System.out.print("Set of Unique Names: ");
        for (String e : employeeNames)
            System.out.print(e + " ");
        System.out.println();

        System.out.println("");

        System.out.println("Employee per Department: ");
        for (Map.Entry<String, List<Employee>> entry : groupedByDepartment.entrySet()) {
            String department = entry.getKey();
            List<Employee> employeeList = entry.getValue();

            System.out.println(department + ": ");
            for (Employee emp : employeeList) {
                System.out.println("-" + emp);
            }
            System.out.println("");
        }

        for (Map.Entry<String, List<Employee>> entry : groupedByDepartment.entrySet()) {
            String department = entry.getKey();
            List<Employee> employeeList = entry.getValue();

            System.out.print("Department: " + department + ", ");

            Employee highestPaid = null;
            for (Employee emp : employeeList) {
                if (highestPaid == null || emp.getSalary() > highestPaid.getSalary()) {
                    highestPaid = emp;
                }
            }

            if (highestPaid != null)
                System.out.println("Highest Paid: " + highestPaid.getName() + " ($" + highestPaid.getSalary() + ")");
        }

        System.out.println("");       

        System.out.println("Descending Salary: " + employees);

        System.out.println("");       

        System.out.println("Ascending Unique Salaries: " + uniqueSalaries);
        System.out.println("");       

    }
}
