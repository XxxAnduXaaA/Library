package com.example.Library.entity.enums;

public enum CategoryType {
    TEXTBOOK("Учебник"),               // Учебник
    METHODOLOGICAL_GUIDE("Методическое пособие"),   // Методическое пособие
    REFERENCE_BOOK("Справочник"),         // Справочник
    SCIENTIFIC_PUBLICATION("Научное издание"); // Научное издание

    private final String displayName;

    CategoryType(String displayName){
        this.displayName = displayName;
    }

    public String getDisplayName(){
        return displayName;
    }
}
