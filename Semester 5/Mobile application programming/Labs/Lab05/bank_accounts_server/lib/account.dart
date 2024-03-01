class Account {
  late var id;
  var iban;
  var sold;
  var currency;
  var bank;
  var name;

  Account({
    this.id,
    this.iban,
    this.sold,
    this.currency,
    this.bank,
    this.name
  });

  // Account(this.iban, this.sold, this.currency, this.bank, this.name) {
  //   id = -1;
  // }

  factory Account.fromMap(Map<String, dynamic> json) => new Account(
        id: json['id'],
        iban: json['iban'],
        sold: json['sold'],
        currency: json['currency'],
        bank: json['bank'],
        name: json['name'],
      );

  Map<String, dynamic> toMap() {
    return {
      'id': id,
      'iban': iban,
      'sold': sold,
      'currency': currency,
      'bank': bank,
      'name': name
    };
  }
}