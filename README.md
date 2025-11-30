\# IPWA02-01\_GhostNetFishing



Eine interaktive Webanwendung zur Meldung und Bergung von Geisternetzen.

Die Webseite wurde im Rahmen einer Fallstudie für das Modul "Programmierung von industriellen Informationssysteme mit Java EE" (IPWA02-01) entwickelt.



---



\## Anforderungen



Ein Geisternetz wird durch folgende Attribute beschrieben:

* Standort (GPS-Koordinaten)
* Geschätzte Größe
* Status



Der Status kann folgende Zustände annehmen:

* Gemeldet: Eine Person hat das Geisternetz erfasst.
* Bergung bevorstehend: Eine bergende Person hat die Bergung angekündigt.
* Geborgen: Das Geisternetz wurde erfolgreich geborgen.
* Verschollen: Das Geisternetz ist am angegebenen Standort nicht auffindbar.



Es existieren zwei Rollen: meldende Person und bergende Person. Beide werden durch Namen und Telefonnummer identifiziert. 

Meldungen können optional anonym erfolgen, jedoch nicht bei der Kennzeichnung als verschollen.



Jedes Geisternetz darf nur einer bergenden Person zugewiesen werden, während eine bergende Person mehrere Geisternetze übernehmen kann.





User Stories wurden nach der MoSCoW-Methode priorisiert:



MUST:

1. Als meldende Person möchte ich Geisternetze (optional anonym) erfassen können.

2\. Als bergende Person möchte ich mich für die Bergung eines Geisternetzes eintragen können.

3\. Als bergende Person möchte ich offene Geisternetze einsehen können.

4\. Als bergende Person möchte ich Geisternetze als geborgen markieren können.



COULD:

5\. Als bergende Person möchte ich die noch nicht geborgenen Netze auf einer Weltkarte sehen.

6\. Als bergende Person möchte ich sehen können, wer welche Geisternetze bergen möchte, um sich ggf. abzustimmen und die Bergung umzuverteilen.

7\. Als beliebige Person möchte ich Geisternetze als verschollen melden können.





\## Eingesetzte Technologien



* Spring Boot
* Thymeleaf
* JPA / Hibernate
* H2-Datenbank
* HTML, CSS \& JavaScript
