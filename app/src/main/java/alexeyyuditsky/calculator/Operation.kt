package alexeyyuditsky.calculator

enum class Operation {
    EMPTY {
        override fun toString(): String = ""
    },
    PLUS {
        override fun toString(): String = "+"
    },
    MINUS {
        override fun toString(): String = "-"
    },
}