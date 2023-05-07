# Xinu_mobialk_IT4VZW

#Infók az appról:
-2 animáció: mindkettő a kezdő lapon, az egyik portrait módban, a másik landscapeben

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
       
