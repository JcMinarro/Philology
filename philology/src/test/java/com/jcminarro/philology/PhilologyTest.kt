package com.jcminarro.philology

import org.amshove.kluent.`should be instance of`
import org.amshove.kluent.mock
import org.junit.Test

class PhilologyTest {

    @Test
    fun `Should return a PhilologyContextWrapper`() {
        Philology.wrap(mock()) `should be instance of` PhilologyContextWrapper::class
    }
}