/**
 * Servicio para realizar operaciones matemáticas.
 */
interface CalculatorRequest {
  numero1: number;
  numero2: number;
}

/**
 * Respuesta de la operación matemática.
 */
interface CalculatorResponse {
  resultado: number;
}

const API_URL = process.env.NEXT_PUBLIC_API_URL || '';

/**
 * Servicio para realizar operaciones matemáticas.
 */
export const calculatorService = {
  /**
   * Realiza una operación matemática.
   * @param tipo - El tipo de operación a realizar.
   * @param data - Los datos de la operación.
   * @returns La respuesta de la operación.
   */
  async realizarOperacion(tipo: string, data: CalculatorRequest): Promise<CalculatorResponse> {
    const response = await fetch(`${API_URL}/api/v1/calculadora/${tipo}`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    });

    if (!response.ok) {
      throw new Error(`Error al realizar la operación: ${response.statusText}`);
    }

    return response.json();
  }
}; 