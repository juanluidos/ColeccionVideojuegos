import React from 'react';
import "../styles/App.css"
import 'bootstrap/dist/css/bootstrap.min.css';
import Game from '../components/Game/Game'
import Sidebar from '../components/Sidebar';  // Asegúrate de que la ruta sea correcta

function App() {
  const list =[1,1,1,1,1,1,11,1,1,1,1,1,11,1,1,1,1,1,1,1,11,1,1,1,1,1,11,1,1,1,1,1,1,1,11,1,1,1,1,1,11,1,1,1,1,1,1,1,11,1,1,1,1,1,11,1]
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
              <div className='libreria'>
              {list.map((name, index) => (
                <Game  key={index}></Game>
              ))}
              </div>
              
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default App;
