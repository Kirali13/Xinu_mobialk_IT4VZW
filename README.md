# Xinu_mobialk_IT4VZW

#Infók az appról:

- 2 animáció: mindkettő a kezdő lapon, az egyik portrait módban, a másik landscapeben

- Xiaomi Redmi Note 9-en fejlesztettem, ott reszponzív minden oldal, egyedül a listázásnál valamiért eltűnnek az itemek adatai

- LifeCycle: AppTheme-ben Ondestroy-t overrideoltam, a "zene" megállításásra

- Android permission: hangszóró engedélyezése

- Notification: sikeres regisztációnál, illetve egy item törlésénél notification küld ezekről az app

* CRUD
    1. create: alaptermékek felvitele firestoreba ha az üres
    2. read: firestoreból való adatok olvasása és megjelenítése
    3. update: admin felhasználóval a termék képén kívül mindent frissíteni lehet, egyből be is tölti a változtatásokat
    4. delete: admin felhasználóval lehet törölni terméket

- Firestore lekérdezések: abc és ár szerinti listázás, illetve 5000 FT alatti termékek listázása
- Admin felhasználó: 
    1. email: admin@admin.com
    2. pw: adminvagyokhello

- Bugok/hibák: Lehet, hogy valamiért ha csak az apkból futtatod akkor egyből kidob, mert nem akarja feldobni azt, hogy engedélyt kérjen a médiatartalmakra. Nálam ha andoid studioból futtattam a sajátomon akkor jó volt. Az angedély megadása után sajna resetlnie kell magát, hogy megmutassa az animációt, ezt úgy a legegyszerűbb ha elforgatod a képernyőt aztán vissza és akkor mind2 animációt bedobja. Ha sehogysem akarja megnyitni, akkor az AppThemeben a Permission checkereket és a playmusichoz tartozó dolgokat kikommentezed, majd buildelsz egy apkt akkor lefut csak zene és animációk nélkül :( 
       
