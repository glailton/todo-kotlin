package br.com.grsoft.todo.model

import java.io.Serializable

class Task: Serializable {
    var id: Int? = 0
    var description: String? = null
    constructor() {
    }
    constructor(id: Int?, description: String) {
        this.id = id
        this.description = description
    }
    constructor(description: String) {
        this.description = description
    }
}
