namespace ahmad_jaradat.Domain
{
    public class Product
    {
        public Guid id { get; set; } = Guid.NewGuid();
        public string name { get; set; }
        public string description { get; set; }
        public decimal price { get; set; }
        public DateTime createdAt { get; set; }
        public DateTime UpdatedAt { get; set; }
    }
}
