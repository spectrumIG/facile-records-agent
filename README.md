# Prova tecnica per MOLO17

Il progetto è la realizzazione della prova tecnica per Molo17 e si compone di una lista di birre scaricate dalle API Rest di Brewdog.
La lista è filtrabile ed è possibile vedere il dettaglio di una birra cliccandoci sopra.

Ho cercato di realizzare un'applicazione quanto più possibile completa e solida a livello tecnico compatibilmente con il tempo a mia disposizione e 
con le specifiche datemi.
  
# Architettura 
L'architettura che ho cercato di utilizzare è l'architettura consigliata da Google (MVVM + Single-Source-Of-Truth) con aggiunta di UseCase per
attenersi (o almeno avvicinarsi) ad un'idea di Clean Architecture.
 
La suddivisione dei package è quella cosiddetta "feature package" in cui i package sono dei contenitori quanto più possibile verticali per tutti
i file che riguardano quella feature fino ad arrivare allo strato di Dominio (Business Logic) escluso.

Il Single-Source-Of-Truth pattern prevederbbe anche l'utilizzo di una sorgente dei dati locale che in generale può essere un DB. Ho solo accennato 
alla sua implementazione in questo caso perchè integrare Room, scelta logica in questo caso, per la semplicità dell'app avrebbe dato dei vantaggi
relativi e a causa della mia mancanza di tempo avrebbe potuto portare a dei ritardi che non avrei avuto le energie di gestire. 

L'utilizzo di Room avrebbe portato come conseguenza naturale all'adozione di Paging 3 come strumento per la paginazione della lista. Ho scelto di
 non integrarlo e ho preferito utilizzare una soluzione più semplice per due motivi: 
 1)  Ho utilizzato in passato la versione 2 e non la 3 che pur essendo più semplice a prima vista non è una libreria nota del tutto e quindi non me
  la sono sentita di affrontare il rischio.
 2)  Non essendo una libreria che conosco benissimo avrebbe potuto portare ritardi che mi avrebbero probabilmente portato a sacrificare altre parti
  dell'app,come il testing.
 Ho preferito quindi adottare una soluzione un po' più semplice ma tutto sommato funzionale. Per piccoli progetti e POC senza molte pretese
  è abbastanza utile e l'ho già adottata in passato diverse volte. Il link  al gist è nei commenti della classe.
 
 Ho tentato di implementare data model diversi per layer diversi per lasciare i layer stessi disaccoppiati fra
 loro e favorire il testing. Si è ovviamente utilizzata la Dependency Injection mediante Hilt.
 
 Una nota sul testing: purtroppo negli anni ho avuto difficoltà nel portare questa metodologia all'interno dei luoghi di lavoro
  che ho frequentato. Di conseguenza anche la mia pratica con essi non è delle migliori. Vi prego di tenere presente che ne sono consapevole ed è la
 prima nella lista di lacune personali da colmare al più presto. Ho cercato comunque per quanto ho potuto di mostrare il minimo indispensabile
 (suppongo) delle skill necessarie per metterla in pratica in maniera efficace. Ho utilizzato Mockk rispetto a Mockito perchè l'ho trovata più
  efficace specialmente con suspend function e più semplice da utilizzare.

Il template di progetto: ho utilizzato un template di progetto creato da Nicola Corti (https://github.com/cortinico/kotlin
 -android-template) che contiene al suo interno diversi plugin che sono superflui per questo progetto ma che possono essere comodi in ambienti in
  cui si utilizzano CI server. Inoltre la struttura del progetto è già pronta con alcune dipendenze di base già al suo interno e buildSrc gi
  à configurato per un progetto multimodulo, cosa che trovo molto comoda.
  
 Il repository remoto è un repository PRIVATO su Github e l'ho utilizzato come sicurezza e supporto allo sviluppo grazie all'utilizzo di PullRequest. 
  
 
 

 

