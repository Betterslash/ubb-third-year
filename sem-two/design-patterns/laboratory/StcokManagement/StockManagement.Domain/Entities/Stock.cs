using StockManagement.Application.Enums;

namespace StockManagement.Domain.Entities;


public class Stock : BaseEntity
{
    public Guid Id { get; set; }
    public string Item { get; set; }
    public string LicensePlate { get; set; }
    public DateOnly BestBeforeDate { get; set; }
    public string Location { get; set; }
    public long QuantityOnHand { get; set; }
    public string Lot { get; set; }
    public StockStatus StockStatus { get; set; }
}