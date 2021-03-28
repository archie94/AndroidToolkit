package com.arka.prava.util.model

import android.content.Intent
import android.os.Bundle

/**
 * A class representing the [Intent] and [Bundle] options to invoke
 * [android.app.Activity.startActivity] with additional options of how the activity is to be
 * started.
 */
class ActivityIntentOption(
    val intent: Intent,
    val options: Bundle?
)
