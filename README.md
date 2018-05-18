# KJSON
#### Idiomatic Json parser for Kotlin (Java)

## ¿What is KJSON?
KJSON is a fully compliant, pretty optimized, idiomatic JSON parser. It is written in Kotlin for the Java Virtual Machine.

## ¿Why should I care?
- If you are looking to map POJOs, then this may not be for you (at least not yet, since it is not my main goal, but i do not overlook the posibility)
- If *obj.getJsonObject("captain").getJsonString("name")* does not seem really verbose to you and does not make you want to cry, then your time is better spent elsewhere.
- On the other hand, if you prefer *obj["captain"]["name"]* you may want to keep reading.

## ¿Why another JSON parsing library?
There are many reasons:
* Flexibility: Not being focused on mapping JSON to predefined entities but to dinamically access any field is great for some uses. Maybe an entity is too much work for your needs, or like in my case, the entity you are trying to map is a construct which does not exist until runtime.
* Kotlin Friendly: Using get and set operators in Kotlin is as idiomatic as I can imagine it to be, unlike other parsers, which taint their library with Java verbose semantics.
* Lightweightness: Is this a word? The thing is it is as light as i could think it to be.
* Performance: Ok now, not to say other JSON parsers are not optimized, but i have seen only a few take into consideration that Java is a garbage ~~collected~~ language. Object creation needs implicit handling, which may be expensive in extreme cases. The most overused method for parsing i have seen out there is "substring", which unfortunately may create some overheader (as far as i know, this is not necesarily their fault, since the array deep copying behaviour for substring was introduced in Java 7 update 6)
* Posibility: I could, I did.