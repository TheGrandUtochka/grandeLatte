package io.nangrande.grandelattedashboard.model;

public enum EmployeeGender {
    MAN("Мужчина"),
    WOMAN("Женщина");

    private final String displayGender;

    EmployeeGender(String displayGender) {
        this.displayGender = displayGender;
    }
}
