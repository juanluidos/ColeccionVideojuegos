import React, { useState } from 'react';
import { BsChevronDown, BsChevronUp } from 'react-icons/bs';

function Sidebar() {
  const [activeMenu, setActiveMenu] = useState(null);

  const toggleMenu = (menu) => {
    if (activeMenu === menu) {
      setActiveMenu(null);
    } else {
      setActiveMenu(menu);
    }
  };

  return (
    <div className="d-flex flex-column flex-shrink-0 p-3 bg-light" style={{height: "100vh"}}>
      <ul className="nav nav-pills flex-column mb-auto">
        <li className="nav-item">
          <a href="#" className={`nav-link ${activeMenu === 'informes' ? "active" : ""}`} onClick={() => toggleMenu('informes')}>Todos los informes</a>
        </li>
        <li className="nav-item">
          <button className={`nav-link d-flex justify-content-between align-items-center ${activeMenu === 'progresos' ? "active" : ""}`} onClick={() => toggleMenu('progresos')}>
            Progresos {activeMenu === 'progresos' ? <BsChevronUp /> : <BsChevronDown />}
          </button>
          <div className={activeMenu === 'progresos' ? "collapse show" : "collapse"}>
            <ul className="list-unstyled fw-normal pb-1 small">
              <li><a href="#" className="link-dark rounded d-block px-3 py-2">Subelemento 1</a></li>
              <li><a href="#" className="link-dark rounded d-block px-3 py-2">Subelemento 2</a></li>
            </ul>
          </div>
        </li>
        <li className="nav-item">
          <button className={`nav-link d-flex justify-content-between align-items-center ${activeMenu === 'coleccion' ? "active" : ""}`} onClick={() => toggleMenu('coleccion')}>
            Colección {activeMenu === 'coleccion' ? <BsChevronUp /> : <BsChevronDown />}
          </button>
          <div className={activeMenu === 'coleccion' ? "collapse show" : "collapse"}>
            <ul className="list-unstyled fw-normal pb-1 small">
              <li><a href="#" className="link-dark rounded d-block px-3 py-2">Subelemento 1</a></li>
              <li><a href="#" className="link-dark rounded d-block px-3 py-2">Subelemento 2</a></li>
            </ul>
          </div>
        </li>
      </ul>
    </div>
  );
}

export default Sidebar;
