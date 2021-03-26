Philology 
=========
[![CircleCI](https://circleci.com/gh/JcMinarro/Philology/tree/master.svg?style=svg)](https://circleci.com/gh/JcMinarro/Philology/tree/master)  [ ![Download](https://api.bintray.com/packages/jcminarro/maven/Philology/images/download.svg) ](https://bintray.com/jcminarro/maven/Philology/_latestVersion) [![ko-fi](https://www.ko-fi.com/img/donate_sm.png)](https://ko-fi.com/A50744AD)

An easy way to dynamically replace Strings of your Android App or provide new languages Over-the-air without needed to publish a new release on Google Play.

## Why should I be interested in Philology

### How String resources work on Android?
Android Resources provide us with an easy way to internationalise our App: a file with all the strings used by our App and a copy of it for every language the App is translated to. Android OS does the rest choosing the proper file depending on device's language.

That is perfect and I don't want to stop using it.

### The problem
These strings are hardcoded inside our App. If there's a typo or you find a better way to express something, a new version of the App needs to be deployed to include the newer translation.
This is a slow process and a poor user experience. We all know users take their time to update an app (if they ever do so) and there's also the time Google Play takes to make a new version of an app available to all users.

### How Philology solves this problem?

_Philology_ doesn't replace the way you are using resources in your current Android Development.
Instead, it improves the process by intercepting the value returned from your **hardcoded translation** files inside of the app and check if there is a newer value in the server.
This allows for typo fixing, better wording or even for adding a new language. All in real time, without releasing a new version of the App.

With _Philology_ you could replace hardcoded texts instantly and win time before the new release is done.

## Getting Started

### Dependency
Philology use [ViewPump](https://github.com/InflationX/ViewPump) library to intercept the view inflate process and reword strings resources. It allows you to use other libraries like [Calligraphy](https://github.com/InflationX/Calligraphy) that intercept the view inflate process

```groovy
dependencies {
    compile 'com.jcminarro:Philology:2.1.0'
    compile 'io.github.inflationx:viewpump:2.0.3'
}
```

### Usage

#### Initialize Philology and ViewPump
Define your `PhilologyRepositoryFactory` who provides `PhilologyRepository` according with the selected `Locale` on the device.
Kotlin:
```Kotlin
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        // Init Philology with our PhilologyRepositoryFactory
        Philology.init(MyPhilologyRepositoryFactory)
        // Add PhilologyInterceptor to ViewPump
        // If you are already using Calligraphy you can add both interceptors, there is no problem
        ViewPump.init(ViewPump.builder().addInterceptor(PhilologyInterceptor).build())
    }
}

object MyPhilologyRepositoryFactory : PhilologyRepositoryFactory {
    override fun getPhilologyRepository(locale: Locale): PhilologyRepository? = when (locale.language) {
        Locale.ENGLISH.language -> EnglishPhilologyRepository
        Locale("es", "ES").language -> SpanishPhilologyRepository
        // If we don't support a language we could return null as PhilologyRepository and
        // values from the strings resources file will be used
        else -> null
    }
}

object EnglishPhilologyRepository : PhilologyRepository {
    override fun getText(key: String): CharSequence? = when (key) {
        "label" -> "New value for the `label` key, it could be fetched from a database or an external API server"
        // If we don't want reword a strings we could return null and the value from the string resources file will be used
        else -> null
    }

    override fun getPlural(key: String, quantityString: String): CharSequence?  = when ("${key}_$quantityString") {
        "plurals_label_one" -> "New value for the `plurals_label` key and `one` quantity keyword"
        "plurals_label_other" -> "New value for the `plurals_label` key and `other` quantity keyword"
        // If we don't want reword a plural we could return null and the value from the string resources file will be used
        else -> null
    }

   override fun getTextArray(key: String): Array<CharSequence>? = when (key) {
        "days" -> arrayOf(
            "Monday",
            "Tuesday",
            "Wednesday",
            "Thursday",
            "Friday",
            "Saturday",
            "Saturday"
        )
        // If we don't want reword a string array we could return null and the value from the string resources file will be used
        else -> null
    }
}

object SpanishPhilologyRepository : PhilologyRepository { /* Implementation */ }
```

Java:
```java
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PhilologyRepositoryFactory repositoryFactory = new MyPhilologyRepositoryFactory();
        Philology.INSTANCE.init(repositoryFactory);
        ViewPump.init(ViewPump.builder().addInterceptor(PhilologyInterceptor.INSTANCE).build());
    }
}

public class MyPhilologyRepositoryFactory extends PhilologyRepositoryFactory {
    @Nullable
    @Override
    public PhilologyRepository getPhilologyRepository(@NotNull Locale locale) {
        if (Locale.ENGLISH.getLanguage().equals(locale.getLanguage())) {
            return new EnglishPhilologyRepository();
        }
        return null;
    }
}

public class EnglishPhilologyRepository extends PhilologyRepository {
    @Nullable
    @Override
    public CharSequence getText(@NotNull Resource resource) { /* Implementation */}
}
```

#### Inject into Context
Wrap the `Activity` Context.
Kotlin:
```kotlin
class BaseActivity : AppCompatActivity() {
    private val delegateHolder = PhilologyAppCompatDelegateHolder()
    override fun getDelegate() = delegateHolder.getDelegate(super.getDelegate()) {
        ViewPumpContextWrapper.wrap(Philology.wrap(it))
    }
}
```

Java:
```java
public class BaseActivity extends AppCompatActivity {
    private final PhilologyAppCompatDelegateHolder delegateHolder = new PhilologyAppCompatDelegateHolder();

    @NonNull
    @Override
    public AppCompatDelegate getDelegate() {
        return delegateHolder.getDelegate(super.getDelegate(), new Function1<Context, Context>() {
                    @Override
                    public Context invoke(Context context) {
                        return ViewPumpContextWrapper.wrap(Philology.INSTANCE.wrap(context));
                    }
                });
    }
}
```

_That is all_

#### CustomViews
Philology allows you to reword your own CustomViews, the only that you need to do is provide an implementation of `ViewTransformer` that rewords the text fields used by your CustomView. The `Context` used to inflate your CustomView is already wrapped by the library, so, you can assign the `@StringRes` used on the `.xml` file that will be provided on the `reword()` method.

Kotlin:
```kotlin
object MyCustomViewTransformer : ViewTransformer {
    override fun reword(view: View, attributeSet: AttributeSet): View = view.apply {
        when (this) {
            is MyCustomView -> reword(attributeSet)
        }
    }

    private fun MyCustomView.reword(attributeSet: AttributeSet) {
        @StringRes val textResId = context.getStringResourceId(attributeSet, R.styleable.MyCustomView_text)
        if (textResId > 0) setTextToMyCustomView(textResId)
    }
}
```

Java:
```java
public class MyCustomViewTransformer extends ViewTransformer {
    private static String MY_CUSTOM_ATTRIBUTE_NAME = "text";
    @NotNull
    @Override
    public View reword(@NotNull View view, @NotNull AttributeSet attributeSet) {
        if (view instanceof MyCustomView) {
            MyCustomView myCustomView = (MyCustomView) view;
            int textResId = getStringResourceId(myCustomView.getContext(), attributeSet, R.styleable.MyCustomView_text);
            if (textResId > 0) {
                myCustomView.setTextToMyCustomView(textResId);
            }
        }
        return view;
    }
}
```

After you implement your `ViewTransformer` you need to provide it to `Philology` injecting a `ViewTransformerFactory` by the `init()` method
Kotlin:
```kotlin
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Philology.init(MyPhilologyRepositoryFactory, MyViewTransformerFactory)
        ViewPump.init(ViewPump.builder().addInterceptor(PhilologyInterceptor.INSTANCE).build());
    }
}

object MyViewTransformerFactory : ViewTransformerFactory{
    override fun getViewTransformer(view: View): ViewTransformer = when (view) {
        is MyCustomView -> MyCustomViewTransformer
        else -> null
    }
}
```

Java:
```java

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PhilologyRepositoryFactory repositoryFactory = new MyPhilologyRepositoryFactory();
        ViewTransformerFactory viewTransformerFactory = new MyCustomViewTransformerFactory();
        Philology.INSTANCE.init(repositoryFactory, viewTransformerFactory);
        ViewPump.init(ViewPump.builder().addInterceptor(PhilologyInterceptor.INSTANCE).build());
    }
}

public class MyCustomViewTransformerFactory implements ViewTransformerFactory {
    @Nullable
    @Override
    public ViewTransformer getViewTransformer(@NotNull View view) {
        if (view instanceof MyCustomView) {
            return new MyViewTransformer();
        }
        return null;
    }
}
```

## Do you want to contribute?
Feel free to add any useful feature to the library, we will be glad to improve it with your help.
I'd love to hear about your use case too, especially if it's not covered perfectly.

Developed By
------------

* Jc Miñarro  - <josecarlos.minarro@gmail.com>

<a href="https://twitter.com/el_joker333">
  <img alt="Follow me on Twitter" src="https://image.freepik.com/iconos-gratis/twitter-logo_318-40209.jpg" height="60" width="60"/>
</a>
<a href="https://www.linkedin.com/in/josecarlosminarrogil/">
  <img alt="Add me to Linkedin" src="https://image.freepik.com/iconos-gratis/boton-del-logotipo-linkedin_318-84979.png" height="60" width="60"/>
</a>
<a href="https://medium.com/@jcminarro">
  <img alt="Follow me on Twitter" src="https://cdn-images-1.medium.com/max/1600/1*emiGsBgJu2KHWyjluhKXQw.png" height="60" width="60"/>
</a>

License
-------

    Copyright 2018 Jc Miñarro

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
