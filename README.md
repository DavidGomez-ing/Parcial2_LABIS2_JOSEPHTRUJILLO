# Parcial 2 – Banco de Preguntas Saber PRO

## Arranque
1. RabbitMQ:  docker run -d --name rabbit -p 5672:5672 -p 15672:15672 rabbitmq:3-management   (consola: http://localhost:15672, guest/guest)
2. En 3 terminales:  `mvn spring-boot:run` dentro de validation-service, question-service y lifecycle-service.

## Pruebas en Postman
**1. Crear pregunta (201)** – POST http://localhost:8083/questions
{
  "statement": "¿Cuál es la ciudad blanca de colombia?",
  "justification": "Popayán es la ciudad blanca de colombia",
  "options": [
    {"text": "Cali", "correct": false},
    {"text": "Popayán", "correct": true},
    {"text": "Medellín", "correct": false},
    {"text": "Pereira", "correct": false}
  ]
}
**2. Justificación corta (400)**: mismo body con "justification": "muy corta" (no se guarda).
**3. Solo 3 opciones o 2 correctas (400)**: falla el Builder.
**4. Enviar a revisión (202 + operationId)** – POST http://localhost:8083/questions/1/submit
**5. Verificar transición** – GET http://localhost:8083/questions/1  -> "status": "PENDING_REVIEW"
   (reenviar de nuevo: el Lifecycle falla porque PendingReviewState lanza excepción).
Consola H2: http://localhost:8083/h2-console  (jdbc:h2:mem:questiondb, sa, sin clave).
