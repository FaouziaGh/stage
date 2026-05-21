Feature: Update a periode

Background:
   Given User is logged in on the home page with the correct correct username "faouzia.gharbi111@gmail.com" and correct password "Faouzia@2026!" and role "college"
   When User clicks on menu "Plans d'etudes" then clicks on submenu "Paramétrage" then clicks on subsubmenu "Années scolaires" then clicks on subsubsubmenu "Périodes"
    Then The correct page is displayed with the title "Liste des périodes"
    
   #Update with Valid data
   Scenario Outline: Update a Periode with valid data 
   When User searches and clicks on the update icone of the periode "sssssssssssssssssssssssssssssssssstsssssssssssssss"
   And The update popup that contains the title "Modifier période" is displayed
   Then User update the form with the following data "Periode modifié", "01/10/2026", "20/12/2026" and clicks on update button
   Then The update confirmation message is displayed "La période a été modifiée avec succès."
   Then The Periode is updated successfully
   
   #Update Invalid date (end date before start date)
   Scenario Outline: Update a Periode with invalid data 
   When User searches and clicks on the update icone of the periode "ddd"
   And The update popup that contains the title "Modifier période" is displayed
   Then User update the form with the following data "Périod", "01/10/2026", "30/08/2025" and clicks on update button
   And The update error popup is displayed "Erreur!" and the message "La date de fin doit être supérieure à la date de début." is displayed
    
   
