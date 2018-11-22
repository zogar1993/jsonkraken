# JSONKraken

#### Idiomatic Json parser for Kotlin (JVM)

[![Maven Central](https://img.shields.io/maven-central/v/net.jemzart/jsonkraken.svg)](http://search.maven.org/#search|ga|1|jsonkraken)

## ¿What is JSONKraken?

JSONKraken is a fully compliant, pretty optimized, idiomatic JSON parser. It is written in Kotlin for the Java Virtual Machine.

## ¿Why should I care?

- If you are looking to map POJOs, then this may not be for you (at least not yet, since it is not my main goal, but i do not overlook the posibility)
- If *obj.getJsonObject("captain").getJsonString("name")* does not seem really verbose to you and does not make you want to cry, then your time is better spent elsewhere.
- On the other hand, if you prefer *obj["captain"]["name"]* you may want to keep reading.

## ¿Why another JSON parsing library?

* Flexibility: Not being focused on mapping JSON to predefined entities but to dinamically access any field is great for some uses. Maybe an entity is too much work for your needs, or like in my case, the entity you are trying to map is a construct which does not exist until runtime.
* Kotlin Friendly: Using get and set operators in Kotlin is as idiomatic as I can imagine it to be, untainted by verbose semantics.
* Lightweightness: Is this a word? The thing is it is as light as i could think it to be.
* Performance: Ok now, not to say other JSON parsers are not optimized, but i have seen only a few take into consideration that Java is a garbage ~~collected~~ language.
Its low level implications sometimes elude proper consideration.
* Possibility: I could, I did.

## Getting Started

I will get technical then, but first lets see a quick example.

### Hello world overview

	val json = "{ \"getting\": { \"started\" : \"Hello World\" }}".toJson()
	println(json.toJsonString()) //prints: {"getting":{"started":"Hello World"}}
	println(json["getting"]["started"]) //prints: Hello World

### Dependency management

#### Maven

    <dependencies>
        <dependency>
            <groupId>net.jemzart</groupId>
            <artifactId>jsonkraken</artifactId>    
            <version>1.0.0</version>
        </dependency>
    </dependencies>

#### Gradle
    
    dependencies {
        compile "net.jemzart:jsonkraken:1.0.0"
    }

### Imports

JsonArray and JsonObject are in package *net.jemzart.jsonkraken.values*.

All needed extension methods are in package *net.jemzart.jsonkraken*.

- - -

That pretty much covers the basics. The rest of the document is for you, dear reader, to better understand the details of JSONKraken.

- - -

## Parsing from String to an Object (Deserialization)

We use *.toJson()* for String to Object conversion. Expected output comes in three flavours: JsonArray, JsonObject and non-collections.
While JsonArray and JsonObject are pretty self explanatory conceptually, their behaviour and particularities will be explained in detail later on for the sake of thoroughness.
Non-collections are not what I would say expected use cases, but for the sake of JSON full compliance I have included such responses. In the context of this parser, a non-collection means every value that we may find in a json other than arrays and objects: strings, numbers, booleans and null.
In case of not being able to parse a symbol, an Exception will be thrown.

## Parsing from Object to String (Serialization)

We use *.toJsonString()* for Object to String conversion.
Strings are generated without needless blank space, minimizing its size and readability.
We instead use *.toJsonString(true)* when we want the serialization to be formatted.

## JsonValue

Both JsonArray and JsonObject are a JsonValue.

A JsonValue is always a consistent json representation should it be serialized. This means it verifies the following in all its operations:

- Added an element, its type is valid (JsonValue, Boolean, String, Char or Number, and they may be null).
- Added a JsonValue, it does not provoke a circular reference.

In cases where the validation fails, an exception will be thrown.

For your peace of mind, this validations are not performed when not necessary, which means none when deserializing and only type check (but not circular reference) validation on constructors.

#### Valid Types
Some valid types are altered for consistency:
- A Number element (Byte, Short, Int, Long, Float and Double) will be converted to Double. This should not be an issue except in extreme cases.
- A Char element will be converted to String.

## JsonValue creation from scratch

Both JsonArray and JsonObject can be created by parameterless constructors.

When constructing any of those, varargs are supported, which means you could:

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

A JsonValue has get and set operators so that you can do the following, provided foo is a JsonValue:

	foo[0] = "bar"
	println(foo[0]) //prints: bar
	
Since there is no way to know the type of the return value of the get operator, it returns a nullable Any (Any?). This is why we need to import the Any? get and set operators the library provides to do the following:

    foo[0][0] = "bar"
	println(foo[0][0]) //prints: bar
	
If you try to get an element which does not exist, an exception will be thrown.
	
Here are some other auxiliary methods and properties JsonValue has:
    
	foo.remove(bar) //removes element at index/key bar
	foo.exists(bar) //returns true if there is an element at index/key bar
	foo.clone() //performs a deep clone of the JsonValue
	foo.size //returns the amount of elements in the JsonValue

## JsonObject

- When iterating over a JsonObject, each element of the iteration is a Pair<String, Any?>.
- The *keys* property returns only its keys.
- You can guess on your own what the *values* property does.

## JsonArray

- When iterating over a JsonArray you iterate directly into its elements.
- The *add(element)* method allows you to add an element after the last one.
- The *insert(index, element)* method allows you to add an element at designated index, pushing all items from said to the last element, without replacing any.
- The *set(index, element)* operator allows you to replace an existing element. If said index is unused, indexes between the specified and the actual last of the JsonArray will be filled with null.

## Implementation details
###### (you should not need to know all of this, but maybe you do. It is here for a reason after all)
- Double -0.0 will be turned to Double 0.0 to avoid the weird default comparison behaviour they have
(they are equal if unboxed, bot not equal if boxed).
I do wonder why do they both even exist.
- Since internally all numbers are handled as Double,
be careful with extremely long Long values (higher than 2<sup>53</sup>).
This is a rather uncommon number to be handling,
more so taking into consideration that it is common practice to write such a value as a String,
a habit born from well placed disbelief in json parsers conversion mechanisms.
- There is a method available for JsonValue which I did not talk about before because I wanted to keep things simple.
It is called *references(value)*, and it requires value to be a JsonValue.
I use that method internally to find if value is recursively contained within the caller,
but since I found no real reason not to make it public, there it is.
- Although *references(value)* does not check for self,
all JsonValue insertion mechanisms do validate for A -> A circularity.
- JsonValues get/set operators welcome both Integers and Strings as index/key.
An Integer will be converted to String in the case of JsonObject,
whereas a String will try to be converted to Int in JsonArray,
and will throw an Exception if the cast fails.
- Although JSON specification for objects does not prohibit duplicate keys,
JSONKraken (like every sane parser out there) does not support it.
Only the value of the last duplicate key will be stored when deserializing,
but no Exception will be thrown.
- Whole numbers will be written without its decimal part.
- Formatted serialization is indented with tabs.
I have thought about allowing custom indentation,
but if I did, it would be reasonable to add all other custom serialization options,
and that would make JSONKraken slightly more complex than intended.
This simple yet standard formatting should suffice.