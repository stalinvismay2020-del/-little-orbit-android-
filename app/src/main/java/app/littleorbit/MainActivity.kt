package app.littleorbit

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.widget.*
import android.content.Context

class MainActivity : Activity() {
    private val bg = Color.rgb(12, 15, 31)
    private val panel = Color.rgb(25, 30, 55)
    private val accent = Color.rgb(151, 125, 255)
    private val textColor = Color.rgb(245, 245, 255)
    private lateinit var content: LinearLayout
    private lateinit var status: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = bg
        window.navigationBarColor = bg
        showHome()
    }

    private fun root(): LinearLayout {
        val outer = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(bg)
            setPadding(dp(18), dp(22), dp(18), dp(12))
        }
        val header = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
        }
        val title = TextView(this).apply {
            text = "☾  Little Orbit"
            textSize = 25f
            setTextColor(textColor)
            typeface = Typeface.DEFAULT_BOLD
        }
        header.addView(title, LinearLayout.LayoutParams(0, -2, 1f))
        status = TextView(this).apply {
            text = "Together, wherever"
            textSize = 11f
            setTextColor(accent)
        }
        header.addView(status)
        outer.addView(header)
        val scroll = ScrollView(this)
        content = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(0, dp(18), 0, dp(12))
        }
        scroll.addView(content)
        outer.addView(scroll, LinearLayout.LayoutParams(-1, 0, 1f))
        val nav = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
        }
        listOf("Home", "Messages", "Daily", "Games", "Memories").forEach { label ->
            val b = Button(this).apply {
                text = label
                textSize = 10f
                setTextColor(textColor)
                setBackgroundTintList(android.content.res.ColorStateList.valueOf(panel))
                setOnClickListener { navigate(label) }
            }
            nav.addView(b, LinearLayout.LayoutParams(0, dp(48), 1f))
        }
        outer.addView(nav)
        return outer
    }

    private fun showHome() {
        val outer = root()
        setContentView(outer)
        content.addView(card("Your little universe", "A private space for two hearts, even when miles apart."))
        content.addView(card("💌  Messages", "Keep your conversation close. Messages are stored on this device in this starter build."))
        content.addView(card("🌙  Daily question", "What small thing made you smile today?"))
        content.addView(card("📸  Memories", "Save a little moment you never want to forget."))
        content.addView(card("🎮  Together time", "Choose a mini activity and make time for each other."))
        val note = TextView(this).apply {
            text = "Starter version • Online chat, calling and live presence need a secure backend before they can work between two phones."
            textSize = 12f
            setTextColor(Color.LTGRAY)
            setPadding(dp(4), dp(12), dp(4), dp(12))
        }
        content.addView(note)
    }

    private fun navigate(label: String) {
        when (label) {
            "Home" -> showHome()
            "Messages" -> showMessages()
            "Daily" -> showDaily()
            "Games" -> showGames()
            "Memories" -> showMemories()
        }
    }

    private fun showMessages() {
        val outer = root(); setContentView(outer)
        content.addView(heading("Messages"))
        content.addView(TextView(this).apply {
            text = "This first APK saves notes locally on this phone. It does not yet send messages to another person."
            setTextColor(Color.LTGRAY); textSize = 13f
        })
        val prefs = getSharedPreferences("little_orbit", Context.MODE_PRIVATE)
        val history = TextView(this).apply {
            text = prefs.getString("messages", "No messages yet. Write your first note below.") ?: ""
            setTextColor(textColor); textSize = 15f; setPadding(0, dp(14), 0, dp(14))
        }
        content.addView(history)
        val input = EditText(this).apply {
            hint = "Write a note…"; setTextColor(textColor); setHintTextColor(Color.GRAY)
            setBackgroundTintList(android.content.res.ColorStateList.valueOf(accent))
        }
        content.addView(input)
        content.addView(actionButton("Save note") {
            val message = input.text.toString().trim()
            if (message.isNotEmpty()) {
                val updated = (prefs.getString("messages", "") ?: "").let { old ->
                    if (old.isBlank() || old == "No messages yet. Write your first note below.") message else "$old\n\n$message"
                }
                prefs.edit().putString("messages", updated).apply()
                history.text = updated
                input.setText("")
                Toast.makeText(this, "Saved on this device", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showDaily() {
        val outer = root(); setContentView(outer)
        content.addView(heading("Daily connection"))
        content.addView(card("Today's question", "What is one memory with us that always makes you happy?"))
        content.addView(card("Tiny challenge", "Send one kind message today and name one thing you appreciate."))
        content.addView(TextView(this).apply {
            text = "These prompts work offline. Shared answers and daily streaks will need account sync in a later version."
            setTextColor(Color.LTGRAY); textSize = 13f; setPadding(0, dp(10), 0, 0)
        })
    }

    private fun showGames() {
        val outer = root(); setContentView(outer)
        content.addView(heading("Together time"))
        content.addView(card("💭  This or That", "Tea or coffee? Sunrise or sunset? Mountains or beach? Take turns choosing."))
        content.addView(card("🔎  Guess the memory", "One person describes a shared memory with three clues; the other guesses."))
        content.addView(card("❤️  20 questions", "Take turns asking kind, fun questions and learn something new."))
        content.addView(TextView(this).apply {
            text = "These are offline activity prompts, not live multiplayer games yet."
            setTextColor(Color.LTGRAY); textSize = 13f
        })
    }

    private fun showMemories() {
        val outer = root(); setContentView(outer)
        content.addView(heading("Our memories"))
        val prefs = getSharedPreferences("little_orbit", Context.MODE_PRIVATE)
        val saved = TextView(this).apply {
            text = prefs.getString("memories", "Your saved memories will appear here.") ?: ""
            setTextColor(textColor); textSize = 15f; setPadding(0, dp(12), 0, dp(12))
        }
        content.addView(saved)
        val input = EditText(this).apply {
            hint = "A moment to remember…"; setTextColor(textColor); setHintTextColor(Color.GRAY)
            setBackgroundTintList(android.content.res.ColorStateList.valueOf(accent))
        }
        content.addView(input)
        content.addView(actionButton("Save memory") {
            val memory = input.text.toString().trim()
            if (memory.isNotEmpty()) {
                val old = prefs.getString("memories", "") ?: ""
                val updated = if (old.isBlank() || old == "Your saved memories will appear here.") memory else "$old\n\n$memory"
                prefs.edit().putString("memories", updated).apply()
                saved.text = updated; input.setText("")
                Toast.makeText(this, "Memory saved on this device", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun heading(value: String) = TextView(this).apply {
        text = value; textSize = 24f; typeface = Typeface.DEFAULT_BOLD
        setTextColor(textColor); setPadding(0, 0, 0, dp(14))
    }

    private fun card(title: String, body: String): View {
        val box = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundTintList(android.content.res.ColorStateList.valueOf(panel))
            setBackgroundColor(panel)
            setPadding(dp(16), dp(15), dp(16), dp(15))
        }
        box.addView(TextView(this).apply {
            text = title; textSize = 17f; typeface = Typeface.DEFAULT_BOLD
            setTextColor(textColor)
        })
        box.addView(TextView(this).apply {
            text = body; textSize = 14f; setTextColor(Color.rgb(205, 210, 230))
            setPadding(0, dp(7), 0, 0)
        })
        val lp = LinearLayout.LayoutParams(-1, -2)
        lp.bottomMargin = dp(12)
        box.layoutParams = lp
        return box
    }

    private fun actionButton(label: String, action: () -> Unit) = Button(this).apply {
        text = label
        setTextColor(Color.WHITE)
        setBackgroundTintList(android.content.res.ColorStateList.valueOf(accent))
        setOnClickListener { action() }
    }

    private fun dp(value: Int): Int = (value * resources.displayMetrics.density).toInt()
}
