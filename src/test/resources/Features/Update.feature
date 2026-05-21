Feature: Update a periode

Background:
   Given User is logged in on the home page with the correct correct username "faouzia.gharbi111@gmail.com" and correct password "Faouzia@2026!" and role "college"
   When User clicks on menu "Plans d'etudes" then clicks on submenu "Paramétrage" then clicks on subsubmenu "Années scolaires" then clicks on subsubsubmenu "Périodes"
   Then The correct page is displayed with the title "Liste des périodes"
   
   #Update with Valid data
   Scenario Outline: Update a Periode with valid data 
   When User searches and clicks on the update icone of the periode "periode nb2"
   And The update popup that contains the title "Modifier période" is displayed
   Then User update the form with the following data "periode nb6", "10/10/2026", "30/11/2026" and clicks on update button
   Then The update confirmation message is displayed "La période a été modifiée avec succès."
   Then The Periode is updated successfully
   
   #Update Invalid date (end date before start date)
   Scenario Outline: Update a Periode with invalid dates 
   When User searches and clicks on the update icone of the periode "P19aPériod20"
   And The update popup that contains the title "Modifier période" is displayed
   Then User update the form with the following data "Périod20", "01/10/2026", "30/08/2025" and clicks on update button
   And The update error popup is displayed "Erreur!" and the message "La date de fin doit être supérieure à la date de début." is displayed
    
   # Update with Duplicate name
   Scenario Outline: Update a Periode with an already existing name
    When User searches and clicks on the update icone of the periode "Période25aPériode 2025"
    And The update popup that contains the title "Modifier période" is displayed
    Then User update the form with the following data "Période 2025", "15/08/2024", "30/069/2025" and clicks on update button
    Then The error popup is displayed "Erreur!" and the message "Le nom de la periode existant :" is displayed
    
   #Update Invalid data (a name containing only special characters )
   Scenario Outline: Update a Periode with invalid Name 
   When User searches and clicks on the update icone of the periode "Périodes_39"
   And The update popup that contains the title "Modifier période" is displayed
   Then User update the form with the following data "@@@/_+", "01/07/2026", "30/08/2026" and clicks on update button
   And The error message is displayed under the field with invalid data "Format invalide"
   
   #Update Invalid data (character length)
   Scenario Outline: Update a Periode with invalid Name 
   When User searches and clicks on the update icone of the periode "Période39a@@@/_+"
   And The update popup that contains the title "Modifier période" is displayed
   Then User update the form with the following data "a", "01/07/2026", "30/08/2026" and clicks on update button
   And The error message "La longueur minimale requise est de 2 caractères." is displayed under the fields with invalid data
   
   #Update a period with empty fields
   Scenario Outline: Update a Periode with empty fields
   When User searches and clicks on the update icone of the periode "Période17"
   And The update popup that contains the title "Modifier période" is displayed
   Then User cleared each field then clicked on update button
   Then Error messages are displayed under each empty field with data "Période17", "22/09/2026"
   
   #Annule - Update a Periode and then cancel it (update a name)
   Scenario: Update a Periode name and then cancel it
   When User searches and clicks on the update icone of the periode "Période13"
   And The update popup that contains the title "Modifier période" is displayed
   Then User update the form with the following data "Période Annulée", "01/07/2026", "30/08/2026" and clicks on cancel button
   Then User is redirected to the period list and "Période Annulée" does not exist

