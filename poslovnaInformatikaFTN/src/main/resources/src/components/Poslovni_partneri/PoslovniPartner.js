import React, { Component } from "react";

export default class PoslovniPartner extends Component {
  render() {
    const {
      pib,
      naziv,
      vrstaPartnera,
      racuniUBanci,
      adresa,
      telefon,
      email,
    } = this.props.poslovniPartner;
    const { deletePoslovniPartner, openModal } = this.props;

    var racuni = "";
    racuniUBanci.forEach((element) => {
      racuni === ""
        ? (racuni += element.nazivBanke + " (" + element.brojRacuna + ")")
        : (racuni +=
            ", " + element.nazivBanke + " (" + element.brojRacuna + ")");
    });
    return (
      <React.Fragment>
        <tr>
          <td>{pib}</td>
          <td>{naziv}</td>
          <td>{vrstaPartnera}</td>
          <td>{racuni}</td>
          <td>{adresa}</td>
          <td>{telefon}</td>
          <td>{email}</td>
          <td>
            <button
              onClick={openModal}
              type="button"
              className="btn btn-primary"
            >
              Izmeni
            </button>
          </td>
          <td>
            <button
              onClick={deletePoslovniPartner}
              type="button"
              className="btn btn-danger"
            >
              Izbriši
            </button>
          </td>
        </tr>
      </React.Fragment>
    );
  }
}
