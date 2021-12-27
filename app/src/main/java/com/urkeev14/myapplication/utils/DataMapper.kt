package com.urkeev14.myapplication.utils

interface DataMapper<in Input, out Output> {
    /**
     * Maps instance of type [Input] into instance of type [Output]
     *
     * @param input to be mapped
     * @return instance of [Output]
     */
    fun map(input: Input): Output

    /**
     * Maps list of type [Input] into list of type [Output]
     *
     * @param inputs to be mapped
     * @return list of [Output]
     */
    fun map(inputs: List<Input>): List<Output> = inputs.map { map(it) }
}
