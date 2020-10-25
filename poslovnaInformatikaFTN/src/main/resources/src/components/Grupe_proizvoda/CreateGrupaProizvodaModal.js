import React, { Component } from "react";
import styled from "styled-components";
import { Link } from "react-router-dom";

export default class CreateGrupaProizvodaModal extends Component {
  state = {
    inputNaziv: "",
    inputIdPdvKategorije: 2,
    inputProizvod: "",
    inputProizvodId: 0,
    proizvodi: [],
    proizvodiToShow: [],
  };

  handleNazivChange = (e) => {
    this.setState({
      inputNaziv: e.target.value,
    });
  };

  handleIdPdvKategorijeChange = (e) => {
    this.setState({
      inputIdPdvKategorije: e.target.value,
    });
  };

  handleProizvodChange = (e) => {
    var index = e.nativeEvent.target.selectedIndex;
    this.setState({
      inputProizvod: e.nativeEvent.target[index].text,
      inputProizvodId: e.target.value,
    });
  };

  addProizvod = () => {
    if (this.state.inputProizvodId !== 0) {
      var proizvodFound = false;
      this.state.proizvodiToShow.forEach((element) => {
        if (element === this.state.inputProizvod) {
          proizvodFound = true;
        }
      });
      if (proizvodFound) {
        alert("Izabrani proizvod je već dodat!");
      } else {
        let tempProizvodi = this.state.proizvodi;
        let tempProizvodiToShow = this.state.proizvodiToShow;
        tempProizvodi.push(this.state.inputProizvodId);
        tempProizvodiToShow.push(this.state.inputProizvod);
        this.setState({
          proizvodi: tempProizvodi,
          proizvodiToShow: tempProizvodiToShow,
        });
      }
    }
  };

  clearProizvodi = () => {
    this.setState({
      proizvodi: [],
      proizvodiToShow: [],
    });
  };

  render() {
    const {
      modalCreateOpen,
      closeModal,
      createGrupaProizvoda,
      proizvodi,
      pdvKategorije,
    } = this.props;

    if (!modalCreateOpen) {
      return null;
    } else {
      return (
        <ModalContainer>
          <div className="container">
            <div className="row">
              <div
                id="modal"
                className="col-8 mx-auto text-center text-capitalize p-5"
              >
                <h3>Dodavanje</h3>
                <hr />
                <br />
                <h5 className="mb-3">
                  naziv: <br />{" "}
                  <input
                    type="text"
                    className="form-control"
                    onChange={this.handleNazivChange}
                  ></input>
                </h5>
                <h5 className="mb-3">
                  PDV Kategorija: <br />{" "}
                  <select
                    type="text"
                    className="form-control"
                    onChange={this.handleIdPdvKategorijeChange}
                  >
                    {pdvKategorije.map((element) => (
                      <option key={element.id} value={element.id}>
                        {element.naziv}
                      </option>
                    ))}
                  </select>
                </h5>
                <h5 className="mb-3">
                  proizvodi: <br />{" "}
                  <div className="input-group form-group">
                    <textarea
                      type="text"
                      className="form-control"
                      value={this.state.proizvodiToShow}
                      readOnly
                    ></textarea>
                    <select
                      id="my-select"
                      type="text"
                      className="form-control"
                      onChange={this.handleProizvodChange}
                    >
                      <option disabled selected value>
                        {" "}
                        -- izaberite proizvod --{" "}
                      </option>
                      {proizvodi.map((element) => (
                        <option key={element.id} value={element.id}>
                          {element.naziv}
                        </option>
                      ))}
                    </select>
                    <button className="btn-add ml-2" onClick={this.addProizvod}>
                      <i className="fas fa-plus" />
                    </button>
                    <button
                      className="btn-clear ml-2"
                      onClick={this.clearProizvodi}
                    >
                      <i className="fas fa-trash" />
                    </button>
                  </div>
                </h5>
                <Link to="/grupe-proizvoda">
                  <button
                    className="btn btn-success m-4"
                    onClick={() => {
                      createGrupaProizvoda(
                        this.state.inputNaziv,
                        this.state.inputIdPdvKategorije,
                        this.state.proizvodi
                      );
                      this.setState({
                        inputNaziv: "",
                        inputIdPdvKategorije: 0,
                        inputProizvod: "",
                        inputProizvodId: 0,
                        proizvodi: [],
                        proizvodiToShow: [],
                      });
                    }}
                  >
                    Dodaj
                  </button>
                </Link>
                <Link to="/grupe-proizvoda">
                  <button
                    className="btn btn-danger m-4"
                    onClick={() => {
                      closeModal();
                      this.setState({
                        inputNaziv: "",
                        inputIdPdvKategorije: 0,
                        inputProizvod: "",
                        inputProizvodId: 0,
                        proizvodi: [],
                        proizvodiToShow: [],
                      });
                    }}
                  >
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
  textarea {
    max-height: 12rem;
  }
  #my-select {
    margin-left: 0.5rem;
  }
  .btn-add {
    color: var(--mainDark);
    width: 2.25rem;
    height: 2.25rem;
    background-color: var(--lightGreen);
    border-radius: 50%;
  }
  .btn-add: hover {
    background-color: var(--mainGreen);
  }
  .btn-clear {
    color: var(--mainDark);
    width: 2.25rem;
    height: 2.25rem;
    background-color: var(--lightRed);
    border-radius: 50%;
  }
  .btn-clear: hover {
    background-color: var(--mediumRed);
  }
`;
