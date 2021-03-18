# Prova tecnica per Facile.it

Il progetto segue o tenta di seguire le specifiche date nel file PDF. 
Sono stati implementati tutti gli Use Cases tranne il checkbox (caso bonus 3)

Le API sono mockate mediante una libreria che randomicamente ritorna errore o i due records come da specifica.

All'interno del progetto c'è una cartella denominata *Coverage* che contiene il report per la Code Coverage generata da Android Studio.
Spero non sia eccessivamente grande. Nel qual caso vi prego di dirmelo e troverò un rimedio.

Ho cercato di fare più test che potevo ma come vi ho anticipato non sono il mio forte ( e ripeto che questo è un problema che sto cercando di
 risolvere ) ho comunque tentato di fare il meglio che potevo con il poco tempo a disposizione. Spero sia rappresentativo.
 
Il progetto è stato testato su un Pixel 3A e su alcuni emulatori con Android Studio 4.1.3 (Stable), Gradle Plugin 4.1.2, Gradle 6.8 e Kotlin 1.4.31 
                                                                                                        
La base di partenza è un template di progetto creato da Nicola Corti (https://github.com/cortinico/kotlin-android-template) che contiene al suo
interno diversi plugin che sono superflui per questo progetto ma che possono essere comodi in ambienti in cui si utilizzano CI server. Inoltre la
struttura del progetto è già pronta con alcune dipendenze di base già al suo interno e buildSrc già configurato per un progetto multimodulo, cosa che
trovo in generale molto comoda.


                                                   
                                                                                                        
                                                                                                        
 

