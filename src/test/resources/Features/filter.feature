Feature: Filter periode

Background:
   Given User is logged in on the home page with the correct correct username "faouzia.gharbi111@gmail.com" and correct password "Faouzia@2026!" and role "college"
   When User clicks on menu "Plans d'etudes" then clicks on submenu "Paramétrage" then clicks on subsubmenu "Années scolaires" then clicks on subsubsubmenu "Périodes"
   Then The correct page is displayed with the title "Liste des périodes"
   
   #Find the periodes of intervals for the start date