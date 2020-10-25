import React, { Component } from "react";
import { Router, Switch, Route } from "react-router-dom";
import "./App.css";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/js/dist/dropdown";
import history from "./history";
import Navbar from "./components/Navbar";
// import Otpremnica from "./components/Otpremnica/Otpremnica";
import Login from "./components/Login";
import Proizvodi from "./components/Proizvodi/Proizvodi";
import GrupeProizvoda from "./components/Grupe_proizvoda/GrupeProizvoda";
import IzdavanjeFakture from "./components/Faktura/Izdavanje_fakture/IzdavanjeFakture";
import IzdavanjePredracuna from "./components/Predracun/Izdavanje_predracuna/IzdavanjePredracuna.js";
import KnjigaIzdatihPredracuna from "./components/Predracun/Knjiga_izdatih_predracuna/KnjigaIzdatihPredracuna";
import KnjigaIzdatihFaktura from "./components/Faktura/Knjiga_izdatih_faktura/KnjigaIzdatihFaktura";
import PdvKategorije from "./components/PDV/Kategorije/PdvKategorije";
import PoslovniPartneri from "./components/Poslovni_partneri/PoslovniPartneri";
import Informacije from "./components/Preduzece/Informacije";
import Korisnici from "./components/Zaposleni/Korisnici";
import Cenovnik from "./components/Cenovnik/Cenovnik";
import PoslovneGodine from "./components/Poslovne_godine/PoslovneGodine";

export class App extends Component {
  render() {
    return (
      <React.Fragment>
        <Router history={history}>
          <Navbar />
          <Switch>
            <Login exact path="/" component={Login} />
            <Route path="/proizvodi" component={Proizvodi} />
            <Route path="/grupe-proizvoda" component={GrupeProizvoda} />
            <Route
              path="/izdavanje-predracuna"
              component={IzdavanjePredracuna}
            />
            <Route path="/izdavanje-fakture" component={IzdavanjeFakture} />
            <Route
              path="/knjiga-predracuna"
              component={KnjigaIzdatihPredracuna}
            />
            <Route path="/knjiga-faktura" component={KnjigaIzdatihFaktura} />
            <Route path="/pdv-kategorije" component={PdvKategorije} />
            <Route path="/poslovni-partneri" component={PoslovniPartneri} />
            <Route path="/zaposleni" component={Korisnici} />
            <Route path="/cenovnik" component={Cenovnik} />
            <Route path="/informacije" component={Informacije} />
            <Route path="/poslovne-godine" component={PoslovneGodine} />
            {/* <Route component={Default} /> */}
          </Switch>
        </Router>
      </React.Fragment>
    );
  }
}

export default App;
