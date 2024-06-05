# Proyecto final Teoría de Autómatas
## Analizador léxico simple.
Desarrollado por Pablo Cardona 20310035 
---
Detecta:
- Palabra reservada (if, main, else, switch, case, default, for, do, while, break, int, String, double, char, print)
- Identificador (Inicia con letra, sin espacios en blanco, sin caracteres especiales, excepto el guión bajo)
- Operador relacional (<, <=, >, >=, ==, !=)
- Operador lógico (&&, ||, !)
- Operador aritmético (+, -, *, /, %)
- Incremento ( ++ )
- Decremento ( -- )
- Asignación ( = )
- Número Entero ( Negativo ó positivo)
- Número decimal ( Negativo ó positivo)
- Cadena de caracteres (Con el formato “Cualquier carácter” )
- Comentario ( Con el formato /* */)
- Comentario de Linea ( Con el formato //Cualquier carácter )
- Parentesis ( (,) )
- Llave ( {, } )

Utilizando como delimitadores: espacio, tabulador y fin de linea.

Mapea un autómata finito determinista, evauando caracter por caracter

