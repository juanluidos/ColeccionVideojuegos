import React from 'react';
import "../styles/App.css"
import 'bootstrap/dist/css/bootstrap.min.css';
import Sidebar from '../components/Sidebar';  // Asegúrate de que la ruta sea correcta
import Game from '../components/Game/Game'

function App() {
  return (
    <div>
      <nav className="navbar navbar-expand-lg navbar-light bg-light">
        <div className="container-fluid">
          <a className="navbar-brand" href="#">Logo perfil</a>
          <div className="collapse navbar-collapse" id="navbarNav">
            <form className="d-flex ms-auto">
              <input className="form-control me-2" type="search" placeholder="Buscar informe" aria-label="Search" />
              <button className="btn btn-outline-success" type="submit">Buscar</button>
            </form>
          </div>
        </div>
      </nav>

      <div className="container-fluid">
        <div className="row">
          <div className="col-md-2">
            <Sidebar />  {/* Usando el componente Sidebar */}
          </div>
          <div className="col-md-10">
            <div className="p-3">
              <h2>Contenido Principal</h2>
              <p>Aquí va el contenido único de cada página.</p>
			  <Game title = "Juego de prueba" platform = "ps"></Game>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default App;
