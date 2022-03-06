using Microsoft.EntityFrameworkCore;

namespace StockManagement.Application.Interfaces;

public interface IDbContext
{
    DbSet<T> Set<T>() where T : class;
    Task SaveChangesAsync(CancellationToken cancellationToken);
    void SaveChanges();
}