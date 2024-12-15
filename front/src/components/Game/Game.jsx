import React, { useState, useEffect } from "react";
import "./Game.css";

const Game = () => {
  const [isAnimating, setIsAnimating] = useState(false); // Estado para iniciar y detener la animación
  const [isHover, setIsHover] = useState(false); 
  const [position, setPosition] = useState({}); // Estilos dinámicos
  const [positionini, setPositionini] = useState({}); // Estilos dinámicos
  const [isReturning, setIsReturning] = useState(false); // Estado para saber si estamos volviendo al grid

  const handleMouseEnter = () => {
    if (!isAnimating) {
      setIsHover(true)
    }
  };

  const handleMouseLeave = () => {
    if (!isAnimating) {
      setIsHover(false)
    }
  };


  const handleAnimationToggle = (event) => {
    const element = event.currentTarget;
    const rect = element.getBoundingClientRect(); // Obtener posición inicial del elemento

    if (!isAnimating) {
      // Calcular el centro de la ventana
      const windowWidth = window.innerWidth;
      const windowHeight = window.innerHeight;
      setPositionini(rect)
      const targetX = (windowWidth - rect.width) / 1.4; // Coordenada X centrada
      const targetY = (windowHeight - rect.height) / 3; // Coordenada Y centrada

      // Iniciar animación hacia el centro
      setPosition({
        position: "fixed",
        top: `${rect.top}px`, // Mantener posición antes de la animación
        left: `${rect.left}px`,
        zIndex: 1000,
        transition: "all 1s ease", // Transición para movimiento
      });

      setTimeout(() => {
        // Mover al centro
        setPosition((prev) => ({
          ...prev,
          top: `${targetY}px`,
          left: `${targetX}px`,
        }));
      }, 0);

      setIsAnimating(true);
    } 
    else {
      // Empezar la animación para volver al grid
      setIsReturning(true); // Activar el estado de retorno

      // Restaurar la posición original
      setPosition({
        position: "absolute", // El componente vuelve al grid (posición relativa al contenedor)
        top: `${rect.top}px`,
        left: `${rect.left}px`,
        zIndex: 1000,
        transition: "all 1s ease", // Transición suave para moverse de vuelta

      });

      // Después de que la animación termine, eliminamos el estilo
      setTimeout(() => {
        setPosition((prev) => ({
          ...prev,
          top: `${positionini.top}px`,
          left: `${positionini.left}px`,
        }));
        setIsReturning(false); // Terminamos la animación de vuelta
      }, 0); // Duración de la transición

      setTimeout(() => {
        setPosition((prev) => ({}));
        setIsReturning(false); // Terminamos la animación de vuelta
      }, 1000); // Duración de la transición
    }

    setIsAnimating(!isAnimating); // Cambiar el estado de animación
  };

  return (
    <div className="game-container">
      <div
        className={`cube ${isAnimating ? "animated" : ""} ${isHover ? "hover" : ""}`}
        onMouseEnter={handleMouseEnter}
        onMouseLeave={handleMouseLeave}
        style={position} // Aplicar estilos dinámicos
        onClick={handleAnimationToggle}
      >
        <div className={`front ${isAnimating ? "animated" : ""}`}>front</div>
        <div className={`left ${isAnimating ? "animated" : ""}`}>left</div>
      </div>
    </div>
  );
};

export default Game;
