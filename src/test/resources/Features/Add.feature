Feature: Add Annee Scolaire 

Background:
   Given User is logged in on the home page with the correct correct username "faouzia.gharbi111@gmail.com" and correct password "Faouzia@2026!" and role "college"
    When User clicks on menu "Plans d'etudes" then clicks on submenu "Paramétrage" then clicks on subsubmenu "Années scolaires" then clicks on subsubsubmenu "Périodes"
    Then The correct page is displayed with the title "Liste des périodes"
    When User clicks on button "+Ajouter Période"
    Then The correct popup is displayed with the subtitle "Ajouter Période"

#Valid data
Scenario Outline: Add a Periode with valid data
    And User fills the form with the following data "P_32", "10/10/2026", "30/12/2026" and clicks on save button
    Then The confirmation message is displayed "Succès"
    Then The Annee Scolaire is added successfully

#Invalid data (end date before start date)
Scenario Outline: Add a Periode with invalid data
    And User fills the form with the following data "Période_12", "30/06/2025", "01/09/2024" and clicks on save button
    Then The error popup is displayed "Erreur!" and the message "La date de fin doit être supérieure à la date de début." is displayed

 Scenario Outline: Add a Periode with empty fields
    When User clicks save without filling the form
    Then Error messages are displayed under empty fields with data "test7", "01/09/2024"

  
  # Duplicate name
  Scenario Outline: Add a Periode with an already existing name
    And User fills the form with the following data "Période 2025", "01/09/2024", "30/06/2025" and clicks on save button
    Then The error popup is displayed "Erreur!" and the message "Le nom du periode exist deja : " is displayed
    
   # Ajouter Plus - add multiple periods at once
   Scenario: Add multiple Periodes using Ajouter Plus
   And User fills the first form with the following data "Périodes_29", "22/09/2026", "30/11/2026"
   When User clicks on "+ajouter plus" button 
   And User fills the second form with the following data "Périodes_39", "01/12/2026", "28/02/2027"
   Then User cliks on "+ajouter plus" button
   And User fills the third form with the following data "Périodes_49", "01/03/2027", "30/06/2027"
   And User clicks on save button
   Then The confirmation message is displayed "Succès"
   Then The three Periodes are added successfully
   
   # Ajouter Plus - remove a row
  Scenario: Remove a row added by Ajouter Plus
    And User fills the first form with the following data "Période_59", "22/09/2026", "30/11/2026"
    When User clicks on "+ajouter plus" button 
    And User fills the second form with the following data "Période_79", "01/12/2026", "28/02/2027"
    Then User clicks on the remove button of the first row
    And User clicks on save button
    Then The confirmation message is displayed "Succès"
    Then Only the second Periode is added successfully
    
    # Ajouter Plus - add a row with invalid data
    Scenario: Add a row with invalid data using Ajouter Plus
    And User fills the first form with the following data "@", "22/09/2026", "30/11/2025"
    When User cliks on "+ajouter plus" button
    And User fills the second form with the following data "@", "01/12/2026", "28/02/2027"
    Then User clicks on "+ajouter plus" button
    And User fills the third form with the following data "Période5", "01/03/2027", "30/06/2027"
    And User clicks on save button
    Then The error message is displayed under the field with invalid data "La longueur minimale requise est de 2 caractères."
    
    #Annule - Add a Periode and then cancel it
   Scenario: Add a Periode and then cancel it
    And User fills the form with the following data "Période Annulée", "01/09/2024", "30/06/2025"
    When User clicks on "Annuler" button
    Then User is redirected to the list and "Période Annulée" does not exist in any page
    

   
