package com.example.projexample

import junit.framework.TestCase

class SettingsFragmentTest : TestCase() {

    //checks if the array has appended
    //checking its values by changing to a listt
    fun testAppend() {
        var ent = arrayOf<CharSequence?>("None")
        var entInputs = arrayOf<CharSequence?>("Pizza")
        var result = SettingsFragment().append(ent, entInputs[0])
        assertEquals(result.toMutableList(), arrayOf<CharSequence?>("None", "Pizza").toMutableList())
    }
}