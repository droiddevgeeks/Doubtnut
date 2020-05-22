# Doubtnut
News API to retrieve https://newsapi.org/docs/endpoints/top-headlines.



### Libraries
* [Android Support Library][support-lib]
* [Android Architecture Components][arch]
* [Android Data Binding][data-binding]
* [Dagger 2][dagger2] for dependency injection
* [Retrofit][retrofit] for REST api communication
* [mockito][mockito] for mocking in tests


[Gson](https://code.google.com/p/google-gson/) is another popular choice and being a smaller library than Jackson, you might prefer it to avoid 65k methods limitation. Also, if you are using  

<a name="networklibs"></a>
**Networking, caching.** There are a couple of battle-proven solutions for performing requests to backend servers, which you should use rather than implementing your own client. We recommend basing your stack around [OkHttp](http://square.github.io/okhttp/) for efficient HTTP requests and using [Retrofit](http://square.github.io/retrofit/) to provide a typesafe layer. 


**RxJava** is a library for Reactive Programming, in other words, handling asynchronous events. It is a powerful paradigm, but it also has a steep learning curve. We recommend taking some caution before using this library to architect the entire application. We have written some blog posts on it: [[1]](http://blog.futurice.com/tech-pick-of-the-week-rx-for-net-and-rxjava-for-android), [[2]](http://blog.futurice.com/top-7-tips-for-rxjava-on-android), [[3]](https://gist.github.com/staltz/868e7e9bc2a7b8c1f754), [[4]](http://blog.futurice.com/android-development-has-its-own-swift). For a reference app, our open source app [Freesound Android](https://github.com/futurice/freesound-android) makes extensive use of RxJava 2.

If you have no previous experience with Rx, start by applying it only for responses from app's backend APIs. Alternatively, start by applying it for simple UI event handling, like click events or typing events on a search field. If you are confident in your Rx skills and want to apply it to the whole architecture, then write documentation on all the tricky parts. Keep in mind that another programmer unfamiliar to RxJava might have a very hard time maintaining the project. Do your best to help them understand your code and also Rx.

Use [RxAndroid](https://github.com/ReactiveX/RxAndroid) for Android threading support and [RxBinding](https://github.com/JakeWharton/RxBinding) to easily create Observables from existing Android components.

A collection of samples using the [Architecture Components](https://developer.android.com/arch):

- [Lifecycle-aware components](https://developer.android.com/topic/libraries/architecture/lifecycle)
- [ViewModels](https://developer.android.com/topic/libraries/architecture/viewmodel)
- [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)


### Test Frameworks

**Use [JUnit](https://developer.android.com/training/testing/unit-testing/local-unit-tests.html) for unit testing** Plain, Android dependency-free unit testing on the JVM is best done using [Junit](https://junit.org). 

#### Local Unit Tests
##### ViewModel Tests
Each ViewModel is tested using local unit tests with mock Repository
implementations.
##### Usecase Tests
Each usecase is tested using local unit tests with mockito & junit
##### Repository Tests
Each Repository is tested using local unit tests with mockito & junit

[Medium]https://medium.com/mindorks/effective-livedata-testing-13d17b555d9b

https://medium.com/mindorks/unit-testing-for-viewmodel-19f4d76b20d4

https://medium.com/mindorks/unit-testing-viewmodel-part-2-4a1fa93d656d


