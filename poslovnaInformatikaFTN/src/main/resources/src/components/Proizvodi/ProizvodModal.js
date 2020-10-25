import React, { Component } from "react";
import styled from "styled-components";
import { Link } from "react-router-dom";

export default class ProizvodModal extends Component {
  state = {
    inputNaziv: "",
    inputJedinicaMere: "",
    inputOpis: "",
    inputTipProizvoda: "",
    inputGrupaProizvoda: "",
  };

  handleNazivChange = (e) => {
    this.setState({
      inputNaziv: e.target.value,
    });
  };

  handleJedinicaMereChange = (e) => {
    this.setState({
      inputJedinicaMere: e.target.value,
    });
  };

  handleOpisChange = (e) => {
    this.setState({
      inputOpis: e.target.value,
    });
  };

  handleTipProizvodaChange = (e) => {
    this.setState({
      inputTipProizvoda: e.target.value,
    });
  };

  handleGrupaProizvodaChange = (e) => {
    this.setState({
      inputGrupaProizvoda: e.target.value,
    });
  };

  render() {
    const { modalOpen, closeModal, saveModal, grupeProizvoda } = this.props;
    const {
      id,
      naziv,
      jedinicaMere,
      opis,
      tipProizvoda,
      idGrupe,
    } = this.props.modalProizvod;

    if (!modalOpen) {
      return null;
    } else {
      return (
        <ModalContainer>
          <div className="container">
            <div className="row">
              <div
                id="modal"
                className="col-8 mx-auto col-md-6 col-lg-4 text-center text-capitalize p-5"
              >
                <h3>izmena</h3>
                <hr />
                <br />
                <h5 className="mb-3">
                  naziv: <br />{" "}
                  <input
                    type="text"
                    className="form-control"
                    defaultValue={naziv}
                    onChange={this.handleNazivChange}
                  ></input>
                </h5>
                <h5 className="mb-3">
                  jedinica mere: <br />{" "}
                  <input
                    type="text"
                    className="form-control"
                    defaultValue={jedinicaMere}
                    onChange={this.handleJedinicaMereChange}
                  ></input>
                </h5>
                <h5 className="mb-3">
                  opis: <br />{" "}
                  <input
                    type="text"
                    className="form-control"
                    defaultValue={opis}
                    onChange={this.handleOpisChange}
                  ></input>
                </h5>
                <h5 className="mb-3">
                  tip proizvoda: <br />{" "}
                  <select
                    type="text"
                    className="form-control"
                    onChange={this.handleTipProizvodaChange}
                    defaultValue={tipProizvoda}
                  >
                    <option value="ROBA">ROBA</option>
                    <option value="USLUGA">USLUGA</option>
                  </select>
                </h5>
                <h5 className="mb-3">
                  grupa proizvoda: <br />{" "}
                  <select
                    type="text"
                    className="form-control"
                    onChange={this.handleGrupaProizvodaChange}
                    defaultValue={idGrupe}
                  >
                    {grupeProizvoda.map((item) => (
                      <option key={item.id} value={item.id}>
                        {item.naziv}
                      </option>
                    ))}
                  </select>
                </h5>
                <Link to="/proizvodi">
                  <button
                    className="btn btn-success m-4"
                    onClick={() => {
                      saveModal(
                        id,
                        this.state.inputNaziv,
                        this.state.inputJedinicaMere,
                        this.state.inputOpis,
                        this.state.inputTipProizvoda,
                        this.state.inputGrupaProizvoda
                      );
                      this.setState({
                        inputNaziv: "",
                        inputJedinicaMere: "",
                        inputOpis: "",
                        inputTipProizvoda: "",
                        inputGrupaProizvoda: "",
                      });
                    }}
                  >
                    Izmeni
                  </button>
                </Link>
                <Link to="/proizvodi">
                  <button className="btn btn-danger m-4" onClick={closeModal}>
                    Odustani
                  </button>
                </Link>
              </div>
            </div>
          </div>
        </ModalContainer>
      );
    }
  }
}

const ModalContainer = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.3);
  display: flex;
  align-items: center;
  justify-content: center;
  #modal {
    background: var(--mainWhite);
  }
`;
