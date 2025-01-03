import React from "react";
import "../styles/App.css"
import 'bootstrap/dist/css/bootstrap.min.css';
import Game from '../components/Game/Game'
import Sidebar from '../components/Sidebar';  // Asegúrate de que la ruta sea correcta
import { library } from '@fortawesome/fontawesome-svg-core'
import { fab } from '@fortawesome/free-brands-svg-icons'
import { fas } from '@fortawesome/free-solid-svg-icons'
import { far } from '@fortawesome/free-regular-svg-icons'

function App() {
  
  
  const list = [1, 1, 1, 1, 1, 1, 11, 1, 1, 1, 1, 1, 11, 1, 1, 1, 1, 1, 1, 1, 11, 1, 1, 1, 1, 1, 11, 1, 1, 1, 1, 1, 1, 1, 11, 1, 1, 1, 1, 1, 11, 1, 1, 1, 1, 1, 1, 1, 11, 1, 1, 1, 1, 1, 11, 1]
  return (
    <div>
      <nav className="navbar navbar-expand-lg navbar-light bg-light">
        <div className="container-fluid">
          <a className="navbar-brand" href="#">Logo perfil</a>
          <div className="collapse navbar-collapse" id="navbarNav">
            {/* <form className="d-flex ms-auto">
              <input className="form-control me-2" type="search" placeholder="Buscar informe" aria-label="Search" />
              <button className="btn btn-outline-success" type="submit">Buscar</button>
            </form> */}
          </div>
        </div>
      </nav>

      <div className="container-fluid">
        <div className="row">
          {/* <div className="col-md-2">
            <Sidebar />  }
          </div> */}
          <div className="col-md-16">
            <div className="p-3">
              <div className='libreria'>
                {list.map((name, index) => {
                  if (index % 7 === 0) {
                    return <Game key={index} platform="pc" title="Cyberpunk 2077" />;
                  } else if (index % 5 === 0) {
                    return <Game key={index} platform="xbox" title="Halo Infinite" />;
                  } else if (index % 3 === 0) {
                    return <Game key={index} platform="switch" title="The Legend of Zelda" />;
                  } else if (index % 2 === 0) {
                    return <Game key={index} platform="ds" title="Inazuma Eleven 2" />;
                  } else {
                    return <Game key={index} platform="ps" title="FC 25"  />;
                  }
                })}
              </div>

            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default App;
library.add(fab, fas, far)