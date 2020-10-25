import React, { Component } from "react";

export default class Faktura extends Component {
  render() {
    const { openModal } = this.props;
    const {
      stavkeFakture,
      kupac,
      datumFakture,
      iznos,
      pdv,
      ukupanRabat,
      status,
    } = this.props.faktura;
    var transformDate = new Date(datumFakture);
    var date =
      ("0" + transformDate.getDate()).slice(-2) +
      "/" +
      ("0" + (transformDate.getMonth() + 1)).slice(-2) +
      "/" +
      transformDate.getFullYear();
    var stavke = "";
    stavkeFakture.forEach((element) => {
      stavke === ""
        ? (stavke += element.proizvod.naziv)
        : (stavke += ", " + element.proizvod.naziv);
    });
    return (
      <React.Fragment>
        <tr>
          <td>{stavke}</td>
          <td>{kupac.naziv}</td>
          <td>{date}</td>
          <td>{iznos}</td>
          <td>{pdv}</td>
          <td>{ukupanRabat}</td>
          <td>{status}</td>
          <td>
            <button
              onClick={openModal}
              type="button"
              className="btn btn-primary"
            >
              Detaljnije
            </button>
          </td>
        </tr>
      </React.Fragment>
    );
  }
}
