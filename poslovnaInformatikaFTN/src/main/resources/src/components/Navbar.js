import React, { Component } from "react";
import { Link } from "react-router-dom";
import styled from "styled-components";

export default class Navbar extends Component {
  signOut = () => {
    sessionStorage.removeItem("token");
    sessionStorage.removeItem("podaci");
  };

  render() {
    return (
      <NavWrapper className="navbar navbar-expand-sm bg-dark px-sm-5 justify-content-between">
        <ul className="navbar-nav">
          <li className="nav-item dropdown ml-3">
            {/* eslint-disable-next-line */}
            <a
              className="nav-link dropdown-toggle my-link"
              href="#"
              id="navbarDropdown"
              role="button"
              data-toggle="dropdown"
              aria-haspopup="true"
              aria-expanded="false"
            >
              Usluge
            </a>
            <div
              className="dropdown-menu my-dropdown"
              aria-labelledby="navbarDropdown"
            >
              <Link to="/proizvodi" className="nav-link">
                proizvodi
              </Link>
              <Link to="/grupe-proizvoda" className="nav-link">
                grupe proizvoda
              </Link>
            </div>
          </li>
          <li className="nav-item ml-3">
            <Link to="/cenovnik" className="nav-link">
              cenovnik
            </Link>
          </li>
          <li className="nav-item dropdown ml-3">
            {/* eslint-disable-next-line */}
            <a
              className="nav-link dropdown-toggle my-link"
              href="#"
              id="navbarDropdown"
              role="button"
              data-toggle="dropdown"
              aria-haspopup="true"
              aria-expanded="false"
            >
              Izdavanje
            </a>
            <div
              className="dropdown-menu my-dropdown"
              aria-labelledby="navbarDropdown"
            >
              <Link to="/izdavanje-predracuna" className="nav-link">
                izdavanje predracuna
              </Link>
              <Link to="/izdavanje-fakture" className="nav-link">
                izdavanje fakture
              </Link>
            </div>
          </li>
          <li className="nav-item dropdown ml-3">
            {/* eslint-disable-next-line */}
            <a
              className="nav-link dropdown-toggle my-link"
              href="#"
              id="navbarDropdown"
              role="button"
              data-toggle="dropdown"
              aria-haspopup="true"
              aria-expanded="false"
            >
              Knjiga izdavanja
            </a>
            <div
              className="dropdown-menu my-dropdown"
              aria-labelledby="navbarDropdown"
            >
              <Link to="/knjiga-predracuna" className="nav-link">
                izdati predracuni
              </Link>
              <Link to="/knjiga-faktura" className="nav-link">
                izdate fakture
              </Link>
            </div>
          </li>
          <li className="nav-item dropdown ml-3">
            {/* eslint-disable-next-line */}
            <a
              className="nav-link dropdown-toggle my-link"
              href="#"
              id="navbarDropdown"
              role="button"
              data-toggle="dropdown"
              aria-haspopup="true"
              aria-expanded="false"
            >
              PDV
            </a>
            <div
              className="dropdown-menu my-dropdown"
              aria-labelledby="navbarDropdown"
            >
              <Link to="/pdv-kategorije" className="nav-link">
                PDV kategorije
              </Link>
            </div>
          </li>
          <li className="nav-item ml-3">
            <Link to="/poslovni-partneri" className="nav-link">
              poslovni partneri
            </Link>
          </li>
          <li className="nav-item ml-3">
            <Link to="/zaposleni" className="nav-link">
              zaposleni
            </Link>
          </li>
          <li className="nav-item ml-3">
            <Link to="/poslovne-godine" className="nav-link">
              poslovne godine
            </Link>
          </li>
          <li className="nav-item ml-3">
            <Link to="/informacije" className="nav-link">
              informacije
            </Link>
          </li>
        </ul>
        <ul className="navbar-nav">
          <li className="nav-item">
            <Link to="/" className="nav-link" onClick={this.signOut}>
              odjava
            </Link>
          </li>
        </ul>
      </NavWrapper>
    );
  }
}

const NavWrapper = styled.nav`
  background: var(--mainDark);
  .my-dropdown {
    width: 14rem !important;
    background: var(--mainDark);
  }
  .nav-link {
    color: var(--mainWhite) !important;
    font-size: 1.3rem;
    text-transform: capitalize;
  }
  .nav-link:hover {
    color: var(--mainGray) !important;
  }
`;
