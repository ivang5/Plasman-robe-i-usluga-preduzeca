import React, { Component } from "react";

export default class Predracun extends Component {
  render() {
    const {
      stavkePredracuna,
      kupac,
      iznos,
      pdv,
      ukupanRabat,
      rokIsporuke,
      rokPlacanja,
    } = this.props.predracun;
    var transformDateIsporuka = new Date(rokIsporuke);
    var transformDatePlacanje = new Date(rokPlacanja);
    var dateIsporuka =
      ("0" + transformDateIsporuka.getDate()).slice(-2) +
      "/" +
      ("0" + (transformDateIsporuka.getMonth() + 1)).slice(-2) +
      "/" +
      transformDateIsporuka.getFullYear();
    var datePlacanje =
      ("0" + transformDatePlacanje.getDate()).slice(-2) +
      "/" +
      ("0" + (transformDatePlacanje.getMonth() + 1)).slice(-2) +
      "/" +
      transformDatePlacanje.getFullYear();
    var stavke = "";
    stavkePredracuna.forEach((element) => {
      stavke === ""
        ? (stavke += element.proizvod.naziv)
        : (stavke += ", " + element.proizvod.naziv);
    });
    return (
      <React.Fragment>
        <tr>
          <td>{stavke}</td>
          <td>{kupac.naziv}</td>
          <td>{iznos}</td>
          <td>{pdv}</td>
          <td>{ukupanRabat}</td>
          <td>{dateIsporuka}</td>
          <td>{datePlacanje}</td>
        </tr>
      </React.Fragment>
    );
  }
}
