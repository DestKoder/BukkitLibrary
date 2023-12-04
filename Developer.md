<h1>Документация для Разработчиков</h1>

<h3>Навигация</h3>
<ul>
    <li><a href=""></a></li>
</ul>


<h2 id="add">Подключение билиотеки</h2>
Шаг 1: Добавление репозитория
```xml 
    <repositories>
        <repository>
            <id>destkoder-repo</id>
            <url>https://destkoder.github.io/maven</url>
        </repository>
    </repositories>
```
Шаг 2: Добавление зависимости
```xml
    <dependencies>
        <dependency>
            <groupId>ru.dest</groupId>
            <artifactId>BukkitLibrary</artifactId>
            <version>2.5</version> <!-- Самые последние версии в секции releases-->
            <scope>provided</scope>
        </dependency>
    </dependencies>
```

```yaml
item:
  #ID - текстовый айдишник предмета, например: WOOL, LIME_WOOL и так далее.
  #amount - не обязательный параметер, если вам не нужно указывать конкретное количество
  #или data, указывает на количество предметов.
  #data - устаревший параметер. Используется только для версии 1.12. Представляет сообой
  #дробный айдишник предмета. Например для красной шерсти он будет равен 12,( WOOL:1:12 )
  #Если вам не нужен следующий параметер, то нет нужды ставить ":" т.е
  #для просто шерсти значение поля будет WOOL, для 5 шерсти - WOOL:5, 
  #для красной шерсти(1.12 only) - WOOL:1:12
  material: "ID:<amount>:<data>" 
  #Кастомное название для предмета (не обязательный параметр)
  name: "&aMy Super Item!" 
  #Описание предмета (не обязательный)
  lore: 
    - '&eMy Super Lore'
  #customModelData предмета (не обязательный)
  model: 5
  #Скин головы с сайта minecraft-heads. Только для предметов являющихся головой игрока. (не обязательный)
  texture: "some texture"
  # Зачарования наложенные на предметы
  enchantments:
    EFFICIENCY: 5
  # Кастомные nbt теги предмета (добавлено в версии 2.5)
  tags:
    customModelData: 5
    "minecraft:customModelData": 5
    "someplugin:someData": value
```
