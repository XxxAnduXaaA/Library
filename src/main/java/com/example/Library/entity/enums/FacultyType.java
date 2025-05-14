package com.example.Library.entity.enums;

import java.util.List;

public enum FacultyType {

    AUTOMATION_AND_IT("Институт автоматики и информационных технологий (ИАиИТ)", List.of("Автоматика и управление в технических системах", //
            "Электронные системы и информационная безопасность", // "
            "Информационно-измерительная техника", //
            "Автоматизация и управление технологическими процессами", //
            "Прикладная математика и информатика", //
            "Высшая математика", //
           "Информатика и вычислительная техника")),
    ECONOMIC_AND_HUMANITIES("Институт инженерно-экономического и гуманитарного образования (ИИЭиГО)", List.of("Национальная и мировая экономика")),
    OIL_AND_GAS_TECHNOLOGIES("Институт нефтегазовых технологий (ИНГТ)", List.of("Геология и физические процессы нефтегазового производства", //
            "Бурение нефтяных и газовых скважин", //
            "Разработка и эксплуатация нефтяных и газовых месторождений", //
            "Трубопроводный транспорт", //
            "Химическая технология и промышленная экология", //
            "Физика", //
            "Машины и оборудование нефтегазовых и химических производств"));

    private final String displayName;
    private final List<String> departments;

    FacultyType(String displayName, List<String> departments){
        this.displayName = displayName;
        this.departments = departments;
    }

    public String getDisplayName(){
        return displayName;
    }

    public List<String> getDepartments() {
        return departments;
    }

}
