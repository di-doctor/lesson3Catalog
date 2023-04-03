package com.kiparo.gallery.simple

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.repeatOnLifecycle
import com.kiparo.gallery.R

class ContainerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, BlankFragment.newInstance("menu"))
                .addToBackStack(null)
                .commit()

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, BlankFragment.newInstance("feature"))
                .addToBackStack(null)
                .commit()
        }
    }

    fun fragmentsCount(): Int {
        return supportFragmentManager.fragments.size
    }

    fun backStackSize(): Int {
        return supportFragmentManager.backStackEntryCount
    }

    //2. Add 4 fragments
    /*supportFragmentManager
        .beginTransaction()
        .add(R.id.fragment_container, TestFragment())
        .add(R.id.fragment_container, TestFragment())
        .add(R.id.fragment_container, TestFragment())
        .add(R.id.fragment_container, TestFragment())
        .setReorderingAllowed(true)
        .commit()*/

    //3. Add 1 fragment and replace it with another
    /*supportFragmentManager
        .beginTransaction()
        .add(R.id.fragment_container, TestFragment())
        .replace(R.id.fragment_container, TestFragment())
        .setReorderingAllowed(true)
        .commit()*/

    //4. Add 1 fragment and replace it with another and add another
    /*supportFragmentManager
        .beginTransaction()
        .add(R.id.fragment_container, TestFragment())
        .replace(R.id.fragment_container, TestFragment())
        .add(R.id.fragment_container, TestFragment())
        .setReorderingAllowed(true)
        .commit()*/
    //5. Use addToBackStack null
    /*supportFragmentManager
        .beginTransaction()
        .add(R.id.fragment_container, TestFragment())
        .addToBackStack(null)
        .setReorderingAllowed(true)
        .commit()*/
    //6. Use addToBackStack null and add another fragment
    /*supportFragmentManager
        .beginTransaction()
        .add(R.id.fragment_container, TestFragment())
        .addToBackStack(null)
        .add(R.id.fragment_container, TestFragment())
        .setReorderingAllowed(true)
        .commit()*/
    //7. Use reorderingAllowed true and add description why it is needed
    /*supportFragmentManager
        .beginTransaction()
        .add(R.id.fragment_container, TestFragment())
        .add(R.id.fragment_container, TestFragment())
        .add(R.id.fragment_container, TestFragment())
        .setReorderingAllowed(true)
        .commit()*/
    //8. Use reorderingAllowed true and addToBackStack null
    /* supportFragmentManager
         .beginTransaction()
         .add(R.id.fragment_container, TestFragment())
         .add(R.id.fragment_container, TestFragment())
         .add(R.id.fragment_container, TestFragment())
         .setReorderingAllowed(true)
         .addToBackStack(null)
         .commit()*/
    //9. Use hide and show, pay attention that the data is not updated
//            val fragment = TestFragment.newInstance("1")
//            val fragment1 = TestFragment.newInstance("2")

    /*supportFragmentManager.beginTransaction()
        .add(R.id.fragment_container,fragment)
        .hide(fragment)
        .add(R.id.fragment_container, fragment1)
        .addToBackStack(null)
        .setReorderingAllowed(true)
        .commit()*/
    //19. Show hide in a separate backstack entry with back button
//            supportFragmentManager.beginTransaction()
//                .add(R.id.fragment_container,fragment)
//                .hide(fragment)
//                .addToBackStack(null)
//                .setReorderingAllowed(true)
//                .commit()
//
//            supportFragmentManager.beginTransaction()
//                .add(R.id.fragment_container,fragment1)
//                .addToBackStack(null)
//                .setReorderingAllowed(true)
//                .commit()
    //20. Remember that fragment can live without view
    // so it means that when you replace fragment with another
    // the view of the old fragment will be destroyed only
}
