Feature: Delete Periode 


  Background:
    Given User is logged in on the home page with the correct correct username "faouzia.gharbi111@gmail.com" and correct password "Faouzia@2026!" and role "college"
    When User clicks on menu "Plans d'etudes" then clicks on submenu "Paramétrage" then clicks on subsubmenu "Années scolaires" then clicks on subsubsubmenu "Périodes"
    Then The correct page is displayed with the title "Liste des périodes"

  # Delete a Periode
  Scenario: Delete a Periode
     When User searches and clicks on the delete icon of the periode "sss"
    Then The delete confirmation message is displayed "Vous voulez supprimer cette période ?"
    When User clicks on "Oui, supprimer!"
    Then The success popup is displayed "Suppression !"
    Then User is redirected to the list of périodes and "sss" does not exist in any page

   # Cancel deleting a Periode
  Scenario: Cancel deleting a periode
    When User searches and clicks on the delete icon of the periode "Période39"
    Then The delete confirmation message is displayed "Vous voulez supprimer cette période ?"
    When User clicks on "Annuler"
    Then User is redirected to the list and "Période39" still exists in the list of périodes