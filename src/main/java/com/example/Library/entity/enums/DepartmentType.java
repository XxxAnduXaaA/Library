package com.example.Library.entity.enums;

public enum DepartmentType {

    // ФПГС
    REINFORCED_CONCRETE_STRUCTURES("Железобетонные конструкции"), //
    METAL_AND_WOOD_STRUCTURES("Металлические и деревянные конструкции"), //
    CONSTRUCTION_TECHNOLOGY_AND_MANAGEMENT("Технология и организация строительного производства"), //
    ROADS_AND_GEODESY("Автомобильные дороги и геодезическое сопровождение строительства"), //
    ENGINEERING_GEOLOGY_AND_FOUNDATIONS("Строительная механика, инженерная геология, основания и фундаменты"), //

    // СТФ
    BUILDING_MATERIALS_PRODUCTION("Производство строительных материалов, изделий и конструкций"), //
    COST_ENGINEERING_AND_EXPERTISE("Стоимостной инжиниринг и техническая экспертиза зданий и сооружений"), //
    AUTOMATION_AND_POWER_SUPPLY_IN_CONSTRUCTION("Механизация, автоматизация и энергоснабжение строительства"), //
    WATER_SUPPLY_AND_SEWERAGE("Водоснабжение и водоотведение"), //
    ENVIRONMENTAL_AND_HYDRAULIC_ENGINEERING("Природоохранное и гидротехническое строительство"), //
    HEAT_GAS_SUPPLY_AND_VENTILATION("Теплогазоснабжение и вентиляция"), //

    // ФАиД
    ARCHITECTURE("Архитектура"), //
    HOUSING_AND_PUBLIC_BUILDINGS("Архитектура жилых и общественных зданий"),
    URBAN_PLANNING("Градостроительство"), //
    RECONSTRUCTION_AND_RESTORATION("Реконструкция и реставрация архитектурного наследия"), //
    GRAPHICS_AND_ARTS("Архитектурно-строительная графика и изобразительное искусство"), //
    DESIGN("Дизайн"), //
    INNOVATIVE_DESIGN("Инновационное проектирование"), //

    // ИАиИТ
    AUTOMATION_AND_CONTROL_SYSTEMS("Автоматика и управление в технических системах"), //
    ELECTRONIC_SYSTEMS_AND_INFORMATION_SECURITY("Электронные системы и информационная безопасность"), // "
    INSTRUMENT_ENGINEERING("Информационно-измерительная техника"), //
    PROCESS_CONTROL_AUTOMATION("Автоматизация и управление технологическими процессами"), //
    APPLIED_MATHEMATICS_AND_CS("Прикладная математика и информатика"), //
    HIGHER_MATHEMATICS("Высшая математика"), //
    COMPUTER_SCIENCE_AND_ENGINEERING("Информатика и вычислительная техника"), //

    // ИНГТ
    GEOLOGY_AND_PHYSICS_OF_OIL_AND_GAS("Геология и физические процессы нефтегазового производства"), //
    DRILLING("Бурение нефтяных и газовых скважин"), //
    OIL_AND_GAS_FIELD_DEVELOPMENT("Разработка и эксплуатация нефтяных и газовых месторождений"), //
    PIPELINE_TRANSPORT("Трубопроводный транспорт"), //
    CHEMICAL_TECHNOLOGY_AND_ECOLOGY("Химическая технология и промышленная экология"), //
    PHYSICS( "Физика"), //
    OIL_GAS_AND_CHEMICAL_EQUIPMENT("Машины и оборудование нефтегазовых и химических производств"), //

    // ИИЭГО
    NATIONAL_AND_WORLD_ECONOMY("Национальная и мировая экономика"); //

    private final String displayName;

    DepartmentType(String displayName){
        this.displayName = displayName;
    }

    public String getDisplayName(){
        return displayName;
    }
}
