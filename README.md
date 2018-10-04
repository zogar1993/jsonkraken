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

We use *.toJsonString()* for Object to String conversion.
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

If you need to convert a non native Array collection to JsonArray or JsonObject, you have helpers methods for those too:

	val arr: JsonArray = listOf(1, "one", true).toJsonArray()
	val obj: JsonObject = mapOf("key1" to 1, "key2" to "one", "key3" to true).toJsonObject()

I did not include *toJsonArray* and *toJsonObject* as constructor alternatives because it led to some confusing scenarios.

## Operating with JsonValue

As I have already mentioned, both JsonArray and JsonObject are a JsonValue.
JsonValues have get and set operators so that you can do the following, provided foo is a JsonValue:

	foo[0] = "foo"
	println(foo[0]) //prints: bar
	
Since there is no way to know the type of the return value of the get operator, it returns a nullable Any (Any?). This is why we need to import Any? get and set operators the library provides to do the following:

    foo[0][0] = "bar"
	println(foo[0][0]) //prints: bar
	
Here are some other auxiliary methods and properties JsonValue has:
    
	foo.remove(bar) //removes element at index/key bar
	foo.exists(bar) //returns true if there is an element at index/key bar
	foo.clone() //performs a deep clone of the JsonValue
	foo.size //returns the amount of elements in the JsonValue

A JsonValue is always a consistent json representation should it be serialized. This means it verifies the following in all its operations:

- Added an element, its type is valid (JsonValue, Boolean, String, Byte, Short, Int, Long, Float and Double, and they may be null)
- Added a JsonValue, it does not provoke a circular reference.

In cases where the validation fails, an exception will be thrown.
For your peace of mind, this validations are not performed when not necessary, which means none when deserializing and only type check (but not circular reference) validation on constructors.

## JsonObject

When iterating over a JsonObject, each element of the iteration is a Pair<String, Any?>
The *keys* property returns only its keys.
You can guess on your own what the *values* property does.

## JsonArray

When iterating over a JsonArray you iterate directly into its elements.
The *add(element)* method allows you to add an element after the last one.
The *insert(index, element)* method allows you to add an element at designated index, pushing all items from said to the last element, without replacing any.
The *set(index, element)* operator allows you to replace an existing element. If said index is unused, indexes between the specified and the actual last of the JsonArray will be filled with null.    