# JSONKraken

#### Idiomatic Json parser for Kotlin (Java)

## ¿What is JSONKraken?

JSONKraken is a fully compliant, pretty optimized, idiomatic JSON parser. It is written in Kotlin for the Java Virtual Machine.

## ¿Why should I care?

- If you are looking to map POJOs, then this may not be for you (at least not yet, since it is not my main goal, but i do not overlook the posibility)
- If *arr.getJsonObject("captain").getJsonString("name")* does not seem really verbose to you and does not make you want to cry, then your time is better spent elsewhere.
- On the other hand, if you prefer *arr["captain"]["name"]* you may want to keep reading.

## ¿Why another JSON parsing library?

There are many reasons:
* Flexibility: Not being focused on mapping JSON to predefined entities but to dinamically access any field is great for some uses. Maybe an entity is too much work for your needs, or like in my case, the entity you are trying to map is a construct which does not exist until runtime.
* Kotlin Friendly: Using get and set operators in Kotlin is as idiomatic as I can imagine it to be, untainted by verbose semantics.
* Lightweightness: Is this a word? The thing is it is as light as i could think it to be.
* Performance: Ok now, ot to say other JSON parsers are not optimized, but i have seen only a few take into consideration that Java is a garbage ~~collected~~ language. Object creation needs implicit handling, which may be expensive when improperly handled.
* Possibility: I could, I did.

## Getting Started

I will get technical then, but first lets see a quick example.

### Hello world overview

	val json = "{ \"getting\": { \"started\" : \"Hello World\" }}".toJson()
	println(json.toJsonString()) //prints: {"getting":{"started":"Hello World"}}
	println(json["getting"]["started"]) //prints: Hello World
		
That pretty much covers the basics. The rest of the document is for you, dear reader, to better understand the details of JSONKraken.

JsonArray and JsonObject are in package *net.jemzart.jsonkraken.values*.
All needed extension methods are in package *net.jemzart.jsonkraken*.

## Parsing from String to an Object

We use *.toJson()* for String to Object conversion. Expected output comes in three flavours: JsonArray, JsonObject and non-collections.
While JsonArray and JsonObject are pretty self explanatory conceptually, their behaviour and particularities will be explained in detail later on for the sake of thoroughness.
Non-collections are not what I would say expected use cases, but for the sake of JSON full compliance I have included such responses. In the context of this parser, a non-collection means every value that we may find in a json other than arrays and objects: strings, numbers, booleans and null.
In case of not being able to parse a symbol, an Exception will be thrown.

## Parsing from Object to String

We use *.toJsonString()* for Object to String conversion. Valid types to convert to String are JsonArray, JsonObject, Boolean, String, Byte, Short, Int, Long, Float and Double, and they may be null. Other conversions will result in an Exception being thrown.
Strings are generated without needless blank space, minimizing its size and readability.

## JsonValue creation from scratch

Both JsonArray and JsonObject can be created by parameterless constructors
varargs are supported by creation, which means you could:

    JsonArray(1, "one", true)
    JsonObject("key1" to 1, "key2" to "one", "key3" to true)
        
Or, if you use the spread operator you could even:

	val array = arrayOf(1, "one", true)
	JsonArray(*array)
	val pairs = arrayOf("key1" to 1, "key2" to "one", "key3" to true)
	JsonObject(*pairs)

If you need to convert a non native Array collection to JsonArray or JsonObject, you have helpers methods for those too.

	val arr: JsonArray = listOf(1, "one", true).toJsonArray()
	val obj: JsonObject = mapOf("key1" to 1, "key2" to "one", "key3" to true).toJsonObject()

## Operating with JsonValue

TODO