package com.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class TestController {

    @RequestMapping("/")
    public String home() {
        String result;
        result = new String("TestHtml");
        return result;
    }

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="Stranger") String name, Model model) {

        List<Employee> employees = new LinkedList<>();
        employees.add(new Employee(Position.lead, new ArrayList<>(Arrays.asList({"Coffe drinking", "yelling"}))));
        ArrayList<String> skill = new ArrayList<>();
        skill.add("Working");
        skill.add("Coffe drinking");
        skill.add("Thinking");
        employees.add(new Employee(Position.junior, skill));

        Position[] p = new Position[]{};
        p = p == null || p.length == 0
                ? Position.values() : p;
        List<Position> searchPositions = Arrays.stream(p)
                .collect(Collectors.toList());
        List<String> r = employees == null ? Collections.emptyList() : employees.stream()
                .filter(employee
                        -> searchPositions.contains(employee.getPosition()))
                .flatMap(employee -> employee.getSkills().stream())
                .distinct()
                .collect(Collectors.toList());
        model.addAttribute("name", name);
        model.addAttribute("result", r);
        return "GreetingHtml";
    }


}

private class Employee {

    public Position pos;

    public ArrayList<String> skills;

    public Position getPosition() {
        return pos;
    }

    public void setPos(Position pos) {
        this.pos = pos;
    }

    public List<String> getSkills() {
        return skills;
    }

    public Employee(Position pos, ArrayList<String> skills) {
        this.pos = pos;
        this.skills = skills;
    }
}

enum  Position {
    lead,
    senior,
    middle,
    junior
}