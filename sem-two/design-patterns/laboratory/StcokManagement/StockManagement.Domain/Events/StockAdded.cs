namespace StockManagement.Domain.Events;

public class StockAdded : IEvent
{
    private Guid StockId { get; set; }

    public StockAdded(Guid stockId)
    {
        StockId = stockId;
    }
}