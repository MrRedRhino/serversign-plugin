## MrRedRhino's Serversigns

Für PaperMC 1.18.1, sollte aber auch auf anderen Versionen funktionieren.

Als Ersatz für "BungeeServerSigns" gedacht, weil das in der 1.18 nicht wirklich funktioniert hat.

Config-Layout:

```
servername: (exakt wie in der proxy)
  pos: (Position des Schildes)
    x: -118
    y: 7
    z: 34
  text: (Text auf dem Schild)
    - "line 1"
    - "line 2"
    - "line 3; benutze {} als Platzhalter für die Spieleranzahl"
    - "line 4; benutze § als farb-code prefix, z.B. §c > Rot"
```

`/reload confirm`um die config neu zu laden