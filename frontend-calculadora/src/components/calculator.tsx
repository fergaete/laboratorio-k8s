"use client"

import type React from "react"

import { useState } from "react"
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from "@/components/ui/card"
import { Input } from "@/components/ui/input"
import { Button } from "@/components/ui/button"
import { Label } from "@/components/ui/label"
import { Tabs, TabsList, TabsTrigger } from "@/components/ui/tabs"
import { Calculator, Plus, Minus } from "lucide-react"
import { calculatorService } from "@/services/calculatorService"
import { toast } from "sonner"

export default function CalculadoraComponent() {
  const [numero1, setNumero1] = useState<number | "">("")
  const [numero2, setNumero2] = useState<number | "">("")
  const [resultado, setResultado] = useState<number | null>(null)
  const [operacion, setOperacion] = useState<string>("")
  const [loading, setLoading] = useState(false)

  const handleNumero1Change = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value
    if (value === "" || /^-?\d*\.?\d*$/.test(value)) {
      setNumero1(value === "" ? "" : Number.parseFloat(value))
    }
  }

  const handleNumero2Change = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value
    if (value === "" || /^-?\d*\.?\d*$/.test(value)) {
      setNumero2(value === "" ? "" : Number.parseFloat(value))
    }
  }

  const realizarOperacion = async (tipo: string) => {
    if (numero1 === "" || numero2 === "") {
      return
    }

    setLoading(true)
    setOperacion(tipo)

    try {
      const data = await calculatorService.realizarOperacion(tipo, {
        numero1: numero1 as number,
        numero2: numero2 as number,
      })
      setResultado(data.resultado)
    } catch (error) {
      console.error("Error al realizar la operación:", error)
      toast.error("Error al realizar la operación")
    } finally {
      setLoading(false)
    }
  }

  return (
    <Card className="w-full max-w-md mx-auto">
      <CardHeader className="space-y-1">
        <CardTitle className="text-2xl font-bold text-center flex items-center justify-center gap-2">
          <Calculator className="h-6 w-6" />
          Calculadora
        </CardTitle>
        <CardDescription className="text-center">Ingresa dos números para realizar operaciones</CardDescription>
      </CardHeader>
      <CardContent className="space-y-4">
        <div className="space-y-2">
          <Label htmlFor="numero1">Primer número</Label>
          <Input
            id="numero1"
            type="text"
            inputMode="decimal"
            value={numero1}
            onChange={handleNumero1Change}
            placeholder="Ingresa el primer número"
          />
        </div>
        <div className="space-y-2">
          <Label htmlFor="numero2">Segundo número</Label>
          <Input
            id="numero2"
            type="text"
            inputMode="decimal"
            value={numero2}
            onChange={handleNumero2Change}
            placeholder="Ingresa el segundo número"
          />
        </div>

        <Tabs defaultValue="sumar" className="w-full">
          <TabsList className="grid w-full grid-cols-2">
            <TabsTrigger value="sumar" onClick={() => realizarOperacion("sumar")}>
              <Plus className="h-4 w-4 mr-2" />
              Sumar
            </TabsTrigger>
            <TabsTrigger value="restar" onClick={() => realizarOperacion("restar")}>
              <Minus className="h-4 w-4 mr-2" />
              Restar
            </TabsTrigger>
          </TabsList>
        </Tabs>

        {resultado !== null && (
          <div className="mt-4 p-4 bg-gray-100 dark:bg-gray-800 rounded-md">
            <p className="text-center font-medium">Resultado de {operacion === "sumar" ? "suma" : "resta"}:</p>
            <p className="text-center text-2xl font-bold">{resultado}</p>
          </div>
        )}
      </CardContent>
      <CardFooter className="flex justify-between">
        <Button
          variant="outline"
          onClick={() => {
            setNumero1("")
            setNumero2("")
            setResultado(null)
          }}
        >
          Limpiar
        </Button>
        <Button
          className="bg-blue-500 hover:bg-blue-600"
          disabled={numero1 === "" || numero2 === "" || loading}
          onClick={() => realizarOperacion(operacion || "sumar")}
        >
          {loading ? "Calculando..." : "Calcular"}
        </Button>
      </CardFooter>
    </Card>
  )
}
