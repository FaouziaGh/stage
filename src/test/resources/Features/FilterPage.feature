Feature: Filter periode

  Background:
    Given User is logged in on the home page with the correct correct username "faouzia.gharbi111@gmail.com" and correct password "Faouzia@2026!" and role "college"
    When User clicks on menu "Plans d'etudes" then clicks on submenu "Paramétrage" then clicks on subsubmenu "Années scolaires" then clicks on subsubsubmenu "Périodes"
    Then The correct page is displayed with the title "Liste des périodes"

  # 🔍 Filter by start date
  Scenario: Find a periode by using Start date filter
    When User clicks on "Afficher Filtres" button
    And The filter form appears with title "Periode"
    Then User fills the start date field with the required date "10/02/2026"
    Then All the periodes with start date greater than or equal to "10/02/2026" are displayed